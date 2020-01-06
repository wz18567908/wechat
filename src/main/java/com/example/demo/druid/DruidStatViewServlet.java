package com.example.demo.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Druid 的数据源状态监控
 */
@WebServlet(urlPatterns = "/druid/*",
                   initParams = {
                          @WebInitParam(name = "allow",value = "192.168.0.8"),
                           @WebInitParam(name = "deny",value = "192.168.1.3"),
                           @WebInitParam(name ="loginUsername",value = "admin"),
                           @WebInitParam(name = "loginPassword",value = "123456"),
                           @WebInitParam(name = "resetEnable",value = "false")//禁止html页面上reset All功能
                   })
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 1L;
}
