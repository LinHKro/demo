package com.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.customer.model.SignRecordHistory;
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
 * Created by 2 on 2017-02-10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SignControllerTest {

    @Autowired
    private SignController signController;

    @Test
    public void sign() throws Exception {

    }

    @Test
    public void historyListById() throws Exception {
        List<SignRecordHistory> recordHistoryList = signController.historyListById("100001");
        String json = JSONObject.toJSONString(recordHistoryList);
        json = JSONObject.toJSONStringWithDateFormat(recordHistoryList,"YYYY-MM-DD");
        assertNotNull(recordHistoryList);
    }

    @Test
    public void historyListByDate() throws Exception {
//        List<SignRecordHistory> recordHistoryList = signController.historyListByDate();
//        assertNotNull(recordHistoryList);
    }

}