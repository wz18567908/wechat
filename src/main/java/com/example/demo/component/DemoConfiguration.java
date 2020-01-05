package com.example.demo.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.example.demo.exception.DemoException;
import com.example.demo.utils.StringUtil;

@Component
public class DemoConfiguration {
    private static final Logger logger = Logger.getLogger(DemoConfiguration.class);
    private static final Properties properties = new Properties();

    public DemoConfiguration() throws DemoException {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/wechat.properties");
            properties.load(inputStream);
            trimSpace(properties);
            inputStream.close();
        } catch (IOException e) {
            logger.error(StringUtil.formatErrorLogger(e, "CloudConfiguration"));
            throw new DemoException(e);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(StringUtil.formatErrorLogger(e, "CloudConfiguration"));
                }
            }
        }
    }
 
    public int getJWTValidTimeLength() {
        return Integer.parseInt(properties.getProperty("JWT_VALID_TL_MINUTE"));
    }

    public int getJWTCheckTimeLength() {
        return Integer.parseInt(properties.getProperty("JWT_CHECK_TL_MINUTE"));
    }

    private void trimSpace(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            entry.setValue(entry.getValue().toString().trim());
        }
    }
}