package com.zzbbc.spring.core.models.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import com.zzbbc.spring.core.dtos.impl.UserDto;
import com.zzbbc.spring.core.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "binance_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class User extends BaseModel<UserDto> {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "password")
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "createDate")
    @CreatedDate
    private LocalDateTime createDate;

    @Override
    public UserDto toDto() {
        return new UserDto(this);
    }
}
