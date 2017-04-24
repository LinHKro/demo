package com.customer.repository;

import com.customer.model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2 on 2017-01-28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void test() throws Exception {

//        customerRepository.save(new Customer("aaa", 10));
//        customerRepository.save(new Customer("bbb", 20));
//        customerRepository.save(new Customer("ccc", 30));
//        customerRepository.save(new Customer("ddd", 40));
//        customerRepository.save(new Customer("eee", 50));
//
//        assertEquals(5, customerRepository.findAll().size());

    }
    @Test
    public void test1() throws Exception {

        List<String> stringList = new ArrayList<>();
        stringList.add("100001");
        stringList.add("100002");
        stringList.add("100003");
        stringList.add("100004");
        stringList.forEach(System.out::println);
        stringList.forEach(customerRepository::findByCardId);

    }
}