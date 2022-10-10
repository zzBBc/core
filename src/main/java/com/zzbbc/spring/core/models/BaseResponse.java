package com.zzbbc.spring.core.models;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.zzbbc.spring.core.enums.LogArgEnum;
import lombok.Data;

@Data
public class BaseResponse {
    private int code;

    private String message;

    private String requestId;

    private Object data;

    private BaseResponse() {
        this.requestId = MDC.get(LogArgEnum.TX_ID.name());
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

    public static ResponseEntity<BaseResponse> success(Object data) {
        return ResponseEntity.ok(new BaseResponse(data));
    }
}
