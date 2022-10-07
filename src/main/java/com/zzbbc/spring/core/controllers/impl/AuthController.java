package com.zzbbc.spring.core.controllers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.config.securities.JwtTokenProvider;
import com.zzbbc.spring.core.dtos.AuthRequest;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.models.impl.UserDetailsImpl;
import com.zzbbc.spring.core.services.UserAuthorizationService;
import com.zzbbc.spring.core.tasks.MdcCallable;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthorizationService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserAuthorizationService userService,
            AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public MdcCallable<?> registerUser(@RequestBody AuthRequest authRequest) {
        return new MdcCallable<>(() -> {
            UserDto dto = userService.registerUser(authRequest);

            return BaseResponse.success(dto);
        });
    }

    @PostMapping("/signin")
    public MdcCallable<?> authenticateUser(@RequestBody AuthRequest loginRequest) {
        return new MdcCallable<>(() -> {

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());

            return BaseResponse.success(new HashMap<String, Object>() {
                {
                    put("jwt", jwt);
                    put("roles", roles);
                }
            });
        });

    }
}
