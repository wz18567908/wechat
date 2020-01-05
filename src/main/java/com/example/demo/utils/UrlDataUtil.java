package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

public class UrlDataUtil {
    private static final List<String> controllerMethodNames = new ArrayList<String>();

    /*
     * The data use hard code due to the controller method which not handled is never changed after develope,
     * and this method is the fastest way to get not handled data.
     */
    static {
        controllerMethodNames.add("/login");
        controllerMethodNames.add("/logout");
        controllerMethodNames.add("/user");
        controllerMethodNames.add("/druid");
    }

    public static List<String> getData(){
        return controllerMethodNames;
    }
}
