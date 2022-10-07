package com.zzbbc.spring.core.services;

import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import com.zzbbc.spring.core.dtos.AuthRequest;
import com.zzbbc.spring.core.dtos.impl.UserDto;

public interface UserAuthorizationService {
    UserDto registerUser(AuthRequest authRequest);

    UserDetails findUserDetailsById(UUID id);

    UserDetails findUserDetailsByUserName(String username);
}
