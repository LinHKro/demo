package com.customer.repository;

import com.customer.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 2 on 2017-01-28.
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByUser(String user);
}
