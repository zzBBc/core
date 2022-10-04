package com.zzbbc.spring.core.services.impl;

import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.models.impl.UserDetailsImpl;
import com.zzbbc.spring.core.repositories.impl.UserRepositoryImpl;
import com.zzbbc.spring.core.services.BaseService;
import com.zzbbc.spring.core.validators.ValidatorFactory;

@Service
public class UserService extends BaseService<UserRepositoryImpl, UUID, User, UserDto>
        implements UserDetailsService {

    public UserService(UserRepositoryImpl repository, ValidatorFactory validatorFactory) {
        super(repository, validatorFactory);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user);
    }
}
