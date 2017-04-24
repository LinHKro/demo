package com.customer.model;

import java.util.List;

/**
 * Created by 2 on 2017-02-05.
 */
public class DataTableBean {
    private List aaData;

    private Integer recordsTotal;

    public DataTableBean(List aaData) {
        this.aaData = aaData;
    }

    public Integer getRecordsTotal() {
        return aaData.size();
    }

    public List getAaData() {
        return aaData;
    }

    public void setAaData(List aaData) {
        this.aaData = aaData;
    }
}
