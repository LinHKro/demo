package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.DataTableBean;
import com.customer.repository.CustomerRepository;
import com.framework.baseClass.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 2 on 2017-01-31.
 */
@RestController
public class CustomerController extends BaseController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @RequestMapping("/api/customer/save")
    public Map<String, String> save(@RequestBody Customer customer) {
        try {
            if (customer.getId() == null) {
                customer.setCreateTime(new Date());
            }
            customer.setUpdateTime(new Date());
            customer.setDelFlag(0);
            customerRepository.save(customer);
            //自动生成cardId
            Long card = 100000L+customer.getId();
            customer.setCardId(card.toString());
            customerRepository.save(customer);
            return resultMap("success", "会员注册成功！会员番号为[ "+customer.getCardId()+" ]");
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap("field", "会员注册失败！");
        }
    }

    @RequestMapping("/api/customer/list")
    public List<Customer> list(@RequestBody int page, @RequestBody int num) {
        Pageable pageable = new PageRequest(page, num);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.getContent();
    }

    @RequestMapping("/api/customer/listAll")
    public List<Customer> list() {
        return customerRepository.findByDelFlag(0);
    }

    @RequestMapping("/api/customer/cardList")
    public List<String> cardList() {
        List<Customer> customerList = list();
        List<String> cardList = new ArrayList<>();
        for (Customer customer : customerList) {
            cardList.add(customer.getCardId());
        }
        return cardList;
    }

    @RequestMapping("/api/customer/listAllDataTable")
    public DataTableBean listAllDataTable() {
        return new DataTableBean(customerRepository.findByDelFlag(0));
    }

    @RequestMapping("/api/customer/getById")
    public Customer getById(@RequestBody int id) {
        return customerRepository.findOne((long) id);
    }

    @RequestMapping("/api/customer/delete")
    public Map<String, String> delete(@RequestBody int id) {
        try {
            Customer customer = customerRepository.findOne((long)id);
            customer.setDelFlag(1);
            customerRepository.save(customer);
            return resultMap("success", "会员删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap("field", "会员删除失败！");
        }
    }

}
