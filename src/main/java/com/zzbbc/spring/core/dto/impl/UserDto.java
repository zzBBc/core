package com.zzbbc.spring.core.dto.impl;

import com.zzbbc.spring.core.dto.BaseDto;
import com.zzbbc.spring.core.models.impl.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto<User> {
    private String token;

    public UserDto(User model) {
        super(model);
        this.token = model.getToken();
    }
}
