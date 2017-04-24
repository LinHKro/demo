package com.customer.scheduling;

import com.customer.model.SignRecord;
import com.customer.model.SignRecordHistory;
import com.customer.repository.SignRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2 on 2017-02-11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerStatusSchedulingTest {
    @Test
    public void reSetCustomerStatus() throws Exception {
        customerStatusScheduling.reSetCustomerStatus();
        List<SignRecord> all = signRecordRepository.findAll();
        assertNotNull(all);
        assertTrue(all.size()>0);
        assertEquals(all.get(0).getStatus(),0);
    }

    @Autowired
    SignRecordRepository signRecordRepository;
    @Autowired
    CustomerStatusScheduling customerStatusScheduling;
    @Test
    public void reSetCustomerOnFirstRun() throws Exception {
        customerStatusScheduling.reSetCustomerOnFirstRun();
        List<SignRecord> all = signRecordRepository.findAll();
        assertNotNull(all);
        assertTrue(all.size()>0);
        assertEquals(all.get(0).getStatus(),0);
    }

}