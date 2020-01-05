package com.example.demo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DataFormatUtils {
    private static final String JSON_ERROR = "error";
    private static final String JSON_CTMSG = "message";
    private static final String JSON_SUCCESS = "success";
    private static final String JSON_FAILURE = "failure";
    private static final String JSON_DATA = "data";
    private static final String SUBMIT_DATA = "submitData";
    public static final String UPDATE_JOBIDS = "updateJobIds";

    public static Map<String, Object> format(String message, Object obj, Object submitJobInfo) {
        Map<String, Object> objectMap = format(message, obj);
        objectMap.put(SUBMIT_DATA, submitJobInfo);
        return objectMap;
    }

    public static Map<String, Object> tokenFormat(String message, String token, Object obj) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put(DemoConstants.TOKEN_NAME, token);
        objectMap.put(JSON_ERROR, message);
        objectMap.put(JSON_DATA, obj);
        return objectMap;
    }

    public static Map<String, Object> tokenFormat(String message) {
        return tokenFormat(message, "", "");
    }

    public static Map<String, Object> format(String message, Object obj) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put(JSON_ERROR, message);
        objectMap.put(JSON_DATA, obj);
        return objectMap;
    }

    public static Map<String, Object> format(String message) {
        return format(message, "");
    }

    public static Map<String, Object> formatControl(List<String> success, List<String> failure,
            List<String> updateJobIds, String jobAction) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Map<String, String> messageMap = new HashMap<String, String>();
        String successMsg = null;
        String failureMsg = null;
        if (success.size() > 0) {
            if (!jobAction.equalsIgnoreCase("bpeek")) {
                successMsg = StringUtils.join(success.toArray(), ";").replaceAll("\n", "");
            } else {
                successMsg = StringUtils.join(success.toArray(), "").replaceAll("<< output from stdout >>", "");
            }
        }
        if (failure.size() > 0) {
            failureMsg = StringUtils.join(failure.toArray(), ";").replaceAll("\n", "");
        }
        if (!(successMsg == null || successMsg.isEmpty()) || !(failureMsg == null || failureMsg.isEmpty())) {
            messageMap.put(JSON_SUCCESS, successMsg);
            messageMap.put(JSON_FAILURE, failureMsg);
        }
        objectMap.put(JSON_CTMSG, messageMap);
        if (updateJobIds.size() != 0) {
            objectMap.put(UPDATE_JOBIDS, StringUtils.join(updateJobIds.toArray(), ","));
        } else {
            objectMap.put(UPDATE_JOBIDS, null);
        }
        return objectMap;
    }
}
