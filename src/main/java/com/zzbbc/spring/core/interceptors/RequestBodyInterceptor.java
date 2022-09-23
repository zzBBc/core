package com.zzbbc.spring.core.interceptors;

import java.lang.reflect.Type;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import com.zzbbc.spring.core.log.APILoggingService;

@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {
    @Autowired
    HttpServletRequest request;

    private final APILoggingService loggingService;

    @Autowired
    public RequestBodyInterceptor(APILoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
            MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        loggingService.displayRequest(request, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
