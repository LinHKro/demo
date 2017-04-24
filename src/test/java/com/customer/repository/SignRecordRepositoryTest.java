package com.customer.repository;

import com.customer.model.SignRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2 on 2017-02-05.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SignRecordRepositoryTest {
    @Autowired
    SignRecordRepository signRecordRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Test
    public void findByCustomer_id() throws Exception {
        SignRecord signRecord = new SignRecord();
        signRecord.setCustomer(customerRepository.findOne(Long.valueOf(1)));
        signRecord.setSignCount(0);
        signRecord.setSignDate(new Date());
        signRecord.setStatus(1);
        signRecordRepository.save(signRecord);

        List<SignRecord> signRecords = signRecordRepository.findByCustomer_id(1L);

        assertNotNull(signRecords);
    }
    @Test
    public void reSetCustomerStatus() throws Exception {
        signRecordRepository.reSetCustomerStatus();
        List<SignRecord> all = signRecordRepository.findAll();
        assertNotNull(all);
    }

}