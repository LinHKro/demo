package com.customer.scheduling;

import com.customer.model.SignRecordHistory;
import com.customer.repository.SignRecordHistoryRepository;
import com.customer.repository.SignRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * Created by 2 on 2017-02-07.
 */
@Configuration
@EnableScheduling // 启用定时任务
public class CustomerStatusScheduling {
    private final SignRecordRepository signRecordRepository;

    private final SignRecordHistoryRepository signRecordHistoryRepository;

    @Autowired
    public CustomerStatusScheduling(SignRecordRepository signRecordRepository, SignRecordHistoryRepository signRecordHistoryRepository) {
        this.signRecordRepository = signRecordRepository;
        this.signRecordHistoryRepository = signRecordHistoryRepository;
    }

    @Scheduled(cron ="0 0 0 1/1 * ?")
    public void reSetCustomerStatus(){
        signRecordRepository.reSetCustomerStatus();
    }

    public void reSetCustomerOnFirstRun(){
        Pageable pageable = new PageRequest(1,1, Sort.Direction.DESC,"id");
        Page<SignRecordHistory> page = signRecordHistoryRepository.findAll(pageable);
        if (page == null || page.getContent()==null || page.getContent().size()<1){
            return;
        }
        List<SignRecordHistory> recordHistoryList = page.getContent();
        SignRecordHistory history = recordHistoryList.get(0);
        if (history.getSignDate().before(new Date())){
            reSetCustomerStatus();
        }

    }

}
