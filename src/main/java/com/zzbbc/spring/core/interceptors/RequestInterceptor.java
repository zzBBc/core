package com.zzbbc.spring.core.interceptors;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.zzbbc.spring.core.log.APILoggingService;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final APILoggingService loggingService;

    @Autowired
    public RequestInterceptor(APILoggingService loggingService) {
        this.loggingService = loggingService;
    }

    private static final List<String> methods = Arrays.asList(
            new String[] {HttpMethod.GET.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name()});

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (methods.contains(request.getMethod())) {
            loggingService.displayRequest(request, null);
        }

        return true;
    }

}
