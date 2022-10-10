package com.zzbbc.spring.core.dtos.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.impl.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto<User> {
    private UUID id;
    private String token;
    private String username;
    private LocalDateTime createDate;

    public UserDto(User model) {
        super(model);

        this.token = model.getToken();
        this.id = model.getId();
        this.username = model.getUsername();
        this.createDate = model.getCreateDate();
    }
}
