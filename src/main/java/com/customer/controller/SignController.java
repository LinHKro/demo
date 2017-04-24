package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.SignRecord;
import com.customer.model.SignRecordHistory;
import com.customer.repository.CustomerRepository;
import com.customer.repository.SignRecordHistoryRepository;
import com.customer.repository.SignRecordRepository;
import com.framework.baseClass.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 2 on 2017-02-05.
 */
@RestController
public class SignController extends BaseController {
    private final CustomerRepository customerRepository;
    private final SignRecordHistoryRepository signRecordHistoryRepository;
    private final SignRecordRepository signRecordRepository;

    @Autowired
    public SignController(CustomerRepository customerRepository, SignRecordHistoryRepository signRecordHistoryRepository, SignRecordRepository signRecordRepository) {
        this.customerRepository = customerRepository;
        this.signRecordHistoryRepository = signRecordHistoryRepository;
        this.signRecordRepository = signRecordRepository;
    }

    @RequestMapping("/api/sign/sign")
    public Map<String, String> sign(@RequestBody String cardId) {
        try {
            List<Customer> customerList = customerRepository.findByCardId(cardId);
            if (customerList == null || customerList.size() < 1) {
                return resultMap("field", "会员番号不存在！");
            }
            if (customerList.size() > 1) {
                return resultMap("field", "数据异常,番号 [ " + cardId + " ] 重复");
            }

            Customer customer = customerList.get(0);
            List<SignRecord> signRecordList = signRecordRepository.findByCustomer_id(customer.getId());
            if (signRecordList == null || signRecordList.size() < 1) {
                newSignRecord(customer);
            } else {
                if (!updateSignRecord(signRecordList.get(0))) {
                    return resultMap("field", "会员番号 [ " + cardId + " ] 签到失败！");
                }
            }
            return resultMap("success", "会员签到成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap("field", "会员签到失败！");
        }
    }

    @RequestMapping("/api/sign/historyListById")
    public List<SignRecordHistory> historyListById(@RequestBody String cardId) {
        try {
            return signRecordHistoryRepository.findByCustomer_cardIdAndCustomer_delFlag(cardId, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/api/sign/historyListByDate")
    public List<SignRecordHistory> historyListByDate(@RequestBody String signDate) {
        try {
            Date start = df.parse(signDate);
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.DAY_OF_MONTH, 1);// +1天
            Date end = c.getTime();
            return signRecordHistoryRepository.findBySignDateBetweenAndCustomer_delFlag(start, end, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean updateSignRecord(SignRecord signRecord) {
        if (signRecord.getStatus() == 1) {
            return false;
        }
        signRecord.setStatus(1);
        signRecordRepository.save(signRecord);
        addSignRecordHistory(signRecord);
        return true;
    }

    private void newSignRecord(Customer customer) {
        SignRecord signRecord = new SignRecord();
        signRecord.setCustomer(customer);
        signRecord.setSignCount(0);
        signRecord.setSignDate(new Date());
        signRecord.setStatus(1);
        signRecordRepository.save(signRecord);
        customer.setSignRecord(signRecord);
        customerRepository.save(customer);
        addSignRecordHistory(signRecord);
    }

    private void addSignRecordHistory(SignRecord signRecord) {
        SignRecordHistory history = new SignRecordHistory();
        history.setCustomer(signRecord.getCustomer());
        history.setSignDate(signRecord.getSignDate());
        if (signRecord.getManager() == null) {
            history.setManager("admin");
        } else {
            history.setManager(signRecord.getManager().getName());
        }
        signRecordHistoryRepository.save(history);
    }
}
