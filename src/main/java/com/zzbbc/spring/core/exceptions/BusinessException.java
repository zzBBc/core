package com.zzbbc.spring.core.exceptions;

import com.zzbbc.spring.core.models.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private BaseResponse baseResponse;

    public BusinessException(BaseResponse baseResponse) {
        super(baseResponse.getMessage());

        this.baseResponse = baseResponse;
    }
}
