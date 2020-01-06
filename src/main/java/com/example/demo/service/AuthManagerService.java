package com.example.demo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.component.JWTExecutor;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.AuthorizationException;
import com.example.demo.exception.DemoException;
import com.example.demo.utils.DemoConstants;
import com.example.demo.utils.DesEncrypter;

import io.jsonwebtoken.Claims;

@Service
public class AuthManagerService {


    @Autowired
    private JWTExecutor jwtExecutor;
    
    @Autowired
    private UserDao userDao;

    private Logger logger = Logger.getLogger(AuthManagerService.class);

    public String login(String userName, String password)
            throws AuthenticationException, DemoException, AuthorizationException {
        boolean canLogin = checklogin(userName, password);
        String token = "";
        if (canLogin) {
            try {
                token = createToken(userName);
            } catch (AuthorizationException e) {
                throw new AuthorizationException(DemoConstants.AUTH_GET_TOKEN_MSG);
            }
            // 登录逻辑
            
        } else {
            logger.warn(String.format("Failed to authenticate linux user '%s'.", userName));
            throw new AuthenticationException(DemoConstants.AUTH_LOGIN_MSG);
        }
        return token;
    }

    private boolean checklogin(String userName, String password)
            throws DemoException, AuthorizationException, AuthenticationException {
        boolean hasPermit = false;

        User user = userDao.findUserByName(userName);
        String encryptPwd = DesEncrypter.getInstance().encrypt(password);
//        if (user == null || !hasPermit) {
//            return false;
//        }
        // 验证逻辑
        
        return true;
    }

    public String createToken(String userName) throws AuthorizationException {
        String token = "";
        User user = new User();
        user.setUserName(userName);
        user.setTokenStartTime(System.currentTimeMillis());

        String subject = jwtExecutor.generalSubject(user);
        token = jwtExecutor.createJWT(subject);
        return token;
    }

    public String refreshToken(String token) throws DemoException {
        String newToken = "";
        try {
            Claims claims = jwtExecutor.parseJWT(token);
            String json = claims.getSubject();
            User user = JSONObject.parseObject(json, User.class);
            String userName = user.getUserName();
            long tokenStartTime = user.getTokenStartTime();
            long timeDiff = System.currentTimeMillis() - tokenStartTime;
            long jwtCheckTimeMillis = jwtExecutor.geJWTCheckTimeMillis();

            if (timeDiff > jwtCheckTimeMillis) {
                boolean isValid = checkValidUserByName(userName);
                if (!isValid) {
                    AuthenticationException ex = new AuthenticationException(
                            String.format("Failed to authenticate database user '%s'.", userName));
                    logger.warn(ex.getMessage());
                    throw ex;
                }
                user.setTokenStartTime(System.currentTimeMillis());
            }

            String subject = jwtExecutor.generalSubject(user);
            newToken = jwtExecutor.createJWT(subject);

        } catch (AuthorizationException | AuthenticationException e) {
            DemoException ex = new DemoException(
                    String.format("Failed to general the new token due to %s", e.getMessage()));
            throw ex;
        }
        return newToken;
    }
    
    private boolean checkValidUserByName(String userName) {
        User user = userDao.findUserByName(userName);
        if (user != null) {
            return true;
        }
        return false;
    }

    public User getUserJson(String userName) {
        return userDao.findUserByName(userName);
    }
}
