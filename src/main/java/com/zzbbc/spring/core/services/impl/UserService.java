package com.zzbbc.spring.core.services.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zzbbc.spring.core.dtos.AuthRequest;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.enums.RoleEnum;
import com.zzbbc.spring.core.models.impl.Role;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.models.impl.UserDetailsImpl;
import com.zzbbc.spring.core.repositories.RoleRepository;
import com.zzbbc.spring.core.repositories.impl.RoleRepositoryImpl;
import com.zzbbc.spring.core.repositories.impl.UserRepositoryImpl;
import com.zzbbc.spring.core.services.BaseService;
import com.zzbbc.spring.core.services.UserAuthorizationService;
import com.zzbbc.spring.core.validators.ValidatorFactory;
import com.zzbbc.spring.core.validators.impl.UserValidator;

@Service
public class UserService extends BaseService<UserRepositoryImpl, UUID, User, UserDto>
        implements UserDetailsService, UserAuthorizationService {

    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepositoryImpl repository, ValidatorFactory validatorFactory,
            PasswordEncoder encoder, RoleRepositoryImpl roleRepository) {
        super(repository, validatorFactory);

        this.userValidator = validatorFactory.getUserValidator();
        this.passwordEncoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user);
    }

    @Override
    public UserDto registerUser(AuthRequest authRequest) {
        userValidator.validateRegisterUser(authRequest);

        User user = new User();
        user.setToken("b");
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.getOrInsert(RoleEnum.ROLE_USER);
        roles.add(role);

        user.setRoles(roles);

        return this.save(user).toDto();
    }

    @Override
    public UserDetails findUserDetailsById(UUID id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            return new UserDetailsImpl(optional.get());
        }

        throw new UsernameNotFoundException(id.toString());
    }

    @Override
    public UserDetails findUserDetailsByUserName(String username) {
        User user = repository.findByUsername(username);

        if (Objects.nonNull(user)) {
            return new UserDetailsImpl(user);
        }

        throw new UsernameNotFoundException(username);
    }
}
