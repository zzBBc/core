package com.zzbbc.spring.core.interceptors;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.zzbbc.spring.core.logs.APILoggingService;

@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<Object> {
    private final APILoggingService loggingService;

    @Autowired
    public ResponseBodyInterceptor(APILoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        loggingService.displayResponse(((ServletServerHttpRequest) request).getServletRequest(),
                ((ServletServerHttpResponse) response).getServletResponse(), body);

        MDC.clear();
        return body;
    }
}
