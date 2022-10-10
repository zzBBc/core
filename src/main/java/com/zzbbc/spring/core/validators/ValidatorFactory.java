package com.zzbbc.spring.core.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zzbbc.spring.core.validators.impl.UserValidator;

@Component
public class ValidatorFactory {

    private final UserValidator userValidator;

    @Autowired
    public ValidatorFactory(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }


}
