package com.zzbbc.spring.core.models.impl;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.zzbbc.spring.core.dto.impl.UserDto;
import com.zzbbc.spring.core.models.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "binance_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseModel<UserDto> {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Override
    public UserDto toDto() {
        return new UserDto(this);
    }
}
