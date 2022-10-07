package com.zzbbc.spring.core.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
