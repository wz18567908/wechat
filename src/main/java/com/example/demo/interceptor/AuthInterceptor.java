package com.example.demo.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.exception.DemoException;
import com.example.demo.service.AuthManagerService;
import com.example.demo.utils.DataFormatUtils;
import com.example.demo.utils.DemoConstants;
import com.example.demo.utils.StringUtil;
import com.example.demo.utils.UrlDataUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthManagerService authManagerService;

    private Logger logger = Logger.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        boolean valid = true;
        String token = "";
        String newToken = "";
        Map<String, Object> data = new HashMap<String, Object>();

        String path = request.getRequestURI();
        List<String> urlList = UrlDataUtil.getData();
        for (String url : urlList) {
            if (!url.equals("/login")) {
                if (path.indexOf(url) != -1) {
                    token = request.getParameter(DemoConstants.HTTP_HEADER_AUTHORIZATION);
                    break;
                }
            }
        }
        if(token == null || token.equals("")) {
            token = request.getHeader(DemoConstants.HTTP_HEADER_AUTHORIZATION);
        }
        if (StringUtil.isNullOrEmpty(token)) {
            data = DataFormatUtils.tokenFormat(DemoConstants.AUTH_NO_TOKEN_MSG);
            valid = false;
        }
        
        if (valid) {
            try {
                newToken = authManagerService.refreshToken(token);
            } catch (Exception e) {
                data = DataFormatUtils.tokenFormat(e.getMessage());
                valid = false;
            }
        }

        if (valid) {
            response.setHeader(DemoConstants.HTTP_HEADER_AUTHORIZATION, newToken);
            return true;
        } else {
            String message = JSONObject.toJSONString(data);
            response.setCharacterEncoding(DemoConstants.DEFAULT_CHARSET);
            response.setContentType(DemoConstants.JSON_CHARSET);

            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(message);
                out.flush();
            } catch (IOException e) {
                logger.error(StringUtil.formatErrorLogger(e, "preHandle"));
                new DemoException(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return false;
        }
    }
}
