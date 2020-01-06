package com.example.demo.controller;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.DataFormatUtils;
import com.example.demo.utils.DemoConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api("测试")
@RestController
public class HelloWorldController extends BaseController<AuthManagerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UserService userService;

    @ApiOperation("获取用户")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
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

    @ApiIgnore
    @RequestMapping(value = "/save", method = RequestMethod.GET)
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
