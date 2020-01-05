package com.example.demo.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.DataFormatUtils;
import com.example.demo.utils.DemoConstants;

@RestController
public class HelloWorldController extends BaseController<AuthManagerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public Map<String, Object> index() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", "Hello World");
        return map;
    }
    
    @RequestMapping("/user")
    public Map<String, Object> getUser() {
        User user = new User();
        String message = "";
        try {
            user = userService.selectUser(1);
        } catch (Exception e) {
            message = DemoConstants.GET_USER_ERROR;
            e.printStackTrace();
        }
        return DataFormatUtils.format(message, user);
    }
    
    @RequestMapping("/save")
    public Map<String, Object> saveUser() {
        User user = new User();
        user.setId(2);
        user.setUserName("user2");
        user.setPassword("111");
        user.setTokenStartTime(12321423);
        String message = "";
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            message = "error";
            e.printStackTrace();
        }
        return DataFormatUtils.format(message, user);
    }
}
