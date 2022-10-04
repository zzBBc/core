package com.zzbbc.spring.core.tasks;

import java.util.Map;
import java.util.concurrent.Callable;
import org.slf4j.MDC;

public class MdcCallable<T> implements Callable<T> {
    private final Callable<T> callable;
    private final Map<String, String> context;


    public MdcCallable(Callable<T> callable) {
        this.callable = callable;
        context = MDC.getCopyOfContextMap();
    }


    @Override
    public T call() throws Exception {
        T val = null;
        try {
            MDC.setContextMap(this.context);
            val = callable.call();
        } finally {
            MDC.clear();
        }
        return val;
    }

}
