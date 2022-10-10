package com.zzbbc.spring.core.validators.impl;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zzbbc.spring.core.dtos.AuthRequest;
import com.zzbbc.spring.core.exceptions.BusinessException;
import com.zzbbc.spring.core.repositories.UserRepository;
import com.zzbbc.spring.core.validators.BaseValidator;
import com.zzbbc.spring.core.validators.Validator;

@Component
public class UserValidator extends BaseValidator {

    private UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateRegisterUser(AuthRequest authRequest) {
        Validator.data(authRequest)
                .validate(AuthRequest::getUsername, Objects::isNull,
                        () -> new BusinessException(-1, "Username không được bỏ trống"))
                .validate(AuthRequest::getPassword, Objects::isNull,
                        () -> new BusinessException(-1, "Password không được bỏ trống"));

        if (exist(authRequest)) {
            throw new BusinessException(-1, "Tài khoản đã tồn tại");
        }
    }

    private boolean exist(AuthRequest authRequest) {
        return Objects.nonNull(userRepository.findByUsername(authRequest.getUsername()));
    }

}
