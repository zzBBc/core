package com.zzbbc.spring.core.models.impl;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.models.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "binance_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseModel<UserDto> {
    @Id
    @Column(value = "id")
    // @GeneratedValue(generator = "UUID")
    // @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(value = "token")
    private String token;

    @Override
    public UserDto toDto() {
        return new UserDto(this);
    }
}
