package com.zzbbc.spring.core.models;

import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class BaseResponse {
    private int code;

    private String message;

    private UUID requestId;

    private Object data;

    private BaseResponse() {
        this.requestId = UUID.fromString(MDC.get("tx.id"));
    }

    public BaseResponse(HttpStatus httpStatus) {
        this(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    public BaseResponse(Object data) {
        this(1, "");

        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this();

        this.code = code;
        this.message = message;
    }
}
