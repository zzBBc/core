package com.zzbbc.spring.core.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.zzbbc.spring.core.exceptions.BusinessException;
import com.zzbbc.spring.core.models.BaseResponse;

@ControllerAdvice
public class GlobalFailureHandler extends ResponseEntityExceptionHandler {
    private static Logger LOGGER = LogManager.getLogger(GlobalFailureHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleControllerException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);

        return toErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return toErrorResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

        return toErrorResponse(ex);
    }

    private ResponseEntity<?> toErrorResponse(BusinessException ex) {
        return ResponseEntity.ok(ex.getBaseResponse());
    }

    private ResponseEntity<?> toErrorResponse(HttpStatus httpStatus) {
        return new ResponseEntity<>(new BaseResponse(httpStatus), httpStatus);
    }
}
