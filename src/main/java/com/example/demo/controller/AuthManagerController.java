package com.example.demo.controller;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.AuthorizationException;
import com.example.demo.exception.DemoException;
import com.example.demo.pojo.AuthJson;
import com.example.demo.service.AuthManagerService;
import com.example.demo.utils.DataFormatUtils;
import com.example.demo.utils.DemoConstants;
import com.example.demo.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("登录认证")
@RestController
public class AuthManagerController extends BaseController<AuthManagerController> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AuthManagerService authManagerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request, @RequestBody AuthJson authJson) {
        String message = "";
        String token = "";
        User user = new User();
        String userName = authJson.getUserName();
        String password = authJson.getPassword();
        try {
            token = authManagerService.login(userName, password);
            if (StringUtil.isNotEmpty(token)) {
                user = authManagerService.getUserJson(userName);
            }
        } catch (AuthenticationException | AuthorizationException e) {
            message = e.getMessage();
        } catch (DemoException e) {
            message = DemoConstants.AUTH_LOGIN_MSG;
        } catch (Throwable th) {
            logger.error(StringUtil.formatErrorLogger(th, "login", userName));
            message = DemoConstants.AUTH_LOGIN_MSG;
        }

        return DataFormatUtils.tokenFormat(message, token, user);
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Map<String, Object> login(HttpServletRequest request) {
        return DataFormatUtils.tokenFormat("ceshi", "123456", "ddd");
    }
}
