package com.customer.repository;

import com.customer.model.SignRecordHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by 2 on 2017-01-28.
 */
public interface SignRecordHistoryRepository extends JpaRepository<SignRecordHistory, Long> {

    List<SignRecordHistory> findByCustomer_cardIdAndCustomer_delFlag(String cardId, int delFlag);

    List<SignRecordHistory> findBySignDateBetweenAndCustomer_delFlag(Date start, Date end, int delFlag);

    Page<SignRecordHistory> findAll(Pageable pageable);
}
