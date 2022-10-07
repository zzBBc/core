package com.zzbbc.spring.core.repositories;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.zzbbc.spring.core.enums.RoleEnum;
import com.zzbbc.spring.core.models.impl.Role;

@Repository
public interface RoleRepository extends BaseRepository<UUID, Role> {
    Role getOrInsert(RoleEnum roleUser);
}
