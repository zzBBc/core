package com.zzbbc.spring.core.dtos.impl;

import java.util.UUID;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.enums.RoleEnum;
import com.zzbbc.spring.core.models.impl.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends BaseDto<Role> {
    private UUID id;

    private RoleEnum name;

    public RoleDto(Role model) {
        super(model);

        this.id = model.getId();
        this.name = model.getName();
    }
}
