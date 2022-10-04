package com.zzbbc.spring.core.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.zzbbc.spring.core.log.APILoggingService;

@ControllerAdvice
public class ResponseBodyIntercepter implements ResponseBodyAdvice<Object> {

    private final APILoggingService loggingService;

    @Autowired
    public ResponseBodyIntercepter(APILoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        loggingService.displayResponse(((ServletServerHttpRequest) request).getServletRequest(),
                ((ServletServerHttpResponse) response).getServletResponse(), body);

        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
