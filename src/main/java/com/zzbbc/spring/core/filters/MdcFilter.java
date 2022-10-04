package com.zzbbc.spring.core.filters;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import com.zzbbc.spring.core.enums.LogArgEnum;

@Component
public class MdcFilter extends GenericFilterBean {
    private static final String TX_ID = LogArgEnum.TX_ID.name();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            MDC.put(TX_ID, UUID.randomUUID().toString());

            chain.doFilter(request, response);
        } finally {
            MDC.remove(TX_ID);;
        }
    }

}
