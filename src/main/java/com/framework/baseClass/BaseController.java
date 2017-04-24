package com.framework.baseClass;

import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2 on 2017-02-05.
 */
public class BaseController {
    protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    protected Map<String, String> resultMap(String status, String msg) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", status);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
