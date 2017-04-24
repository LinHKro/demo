package com.customer.repository;

import com.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 2 on 2017-01-28.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByDelFlag(int delFlag);
    List<Customer> findByCardId(String cardId);

}
