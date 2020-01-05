package com.example.demo.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class BaseController<T extends Serializable> {
    protected Logger logger;
    protected SimpleDateFormat dateFormat;

    @SuppressWarnings({ "rawtypes" })
    protected BaseController() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        logger = Logger.getLogger((Class) params[0]);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}