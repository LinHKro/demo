package com.customer.repository;

import com.customer.model.SignRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 2 on 2017-01-28.
 */
public interface SignRecordRepository extends JpaRepository<SignRecord, Long> {
    List<SignRecord> findByCustomer_id(Long id);

    @Modifying(clearAutomatically = true) @Query("update SignRecord as s set s.status = 0")@Transactional
    void reSetCustomerStatus();
}
