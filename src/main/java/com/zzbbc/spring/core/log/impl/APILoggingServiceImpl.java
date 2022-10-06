package com.zzbbc.spring.core.log.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.zzbbc.spring.core.log.APILoggingService;

@Service
public class APILoggingServiceImpl implements APILoggingService {
    private static final Logger LOGGER = LogManager.getLogger(APILoggingServiceImpl.class);

    @Override
    public void displayRequest(HttpServletRequest request, Object body) {
        List<Object> args = new ArrayList<>();

        StringBuilder message = new StringBuilder("logRequest: REQUEST method = [{}] path = [{}]");
        args.add(request.getMethod());
        args.add(request.getRequestURI());

        Map<String, String> parameters = getParameters(request);
        if (!parameters.isEmpty()) {
            message.append(" parameters = [{}]");
            args.add(parameters);
        }

        Map<String, String> headers = getHeaders(request);
        if (!headers.isEmpty()) {
            message.append(" RequestHeaders = [{}]");
            args.add(headers);
        }

        if (!Objects.isNull(body)) {
            message.append(" body = [{}]");
            args.add(body);
        }

        LOGGER.info(message.toString(), args.toArray());
    }

    @Override
    public void displayResponse(HttpServletRequest request, HttpServletResponse response,
            Object body) {
        List<Object> args = new ArrayList<>();

        StringBuilder message = new StringBuilder("logResponse: RESPONSE method = [{}]");
        args.add(request.getMethod());

        Map<String, String> headers = getHeaders(response);
        if (!headers.isEmpty()) {
            message.append(" ResponseHeaders = [{}]");
            args.add(headers);
        }

        message.append(" responseBody = [{}]");
        args.add(body);

        LOGGER.info(message.toString(), args.toArray());
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerMap = request.getHeaderNames();

        while (headerMap.hasMoreElements()) {
            String key = headerMap.nextElement();
            headers.put(key, request.getHeader(key));
        }

        return headers;
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for (String str : headerMap) {
            headers.put(str, response.getHeader(str));
        }
        return headers;
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }
}
