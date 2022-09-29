package com.zzbbc.spring.core.services.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.repositories.impl.UserRepository;
import com.zzbbc.spring.core.services.BaseService;
import com.zzbbc.spring.core.validators.ValidatorFactory;

@Service
public class UserService extends BaseService<UserRepository, UUID, User, UserDto> {

    public UserService(UserRepository repository, ValidatorFactory validatorFactory) {
        super(repository, validatorFactory);
    }
}
