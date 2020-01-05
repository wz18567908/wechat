package com.example.demo.advice;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.demo.utils.DemoConstants;
import com.example.demo.utils.UrlDataUtil;

@ControllerAdvice
public class DemoResponseBodyAdvice implements ResponseBodyAdvice<Map<String, Object>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Map<String, Object> beforeBodyWrite(Map<String, Object> responseBody, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        URI uri = request.getURI();
        String path = uri.getPath();
        List<String> urlList = UrlDataUtil.getData();
        for (String urlName : urlList) {
            if (path.indexOf(urlName) != -1) {
                return responseBody;
            }
        }

        List<String> tokenList = response.getHeaders().get(DemoConstants.HTTP_HEADER_AUTHORIZATION);
        String token = "";
        if (! (tokenList == null || tokenList.isEmpty())) {
            token = tokenList.get(0);
        }
        responseBody.put(DemoConstants.TOKEN_NAME, token);
        return responseBody;
    }
}