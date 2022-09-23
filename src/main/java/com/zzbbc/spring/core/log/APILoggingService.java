package com.zzbbc.spring.core.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface APILoggingService {
    void displayRequest(HttpServletRequest request, Object body);

    void displayResponse(HttpServletRequest request, HttpServletResponse response, Object body);
}
