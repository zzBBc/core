package com.zzbbc.spring.core.repositories;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.zzbbc.spring.core.models.impl.User;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    User findByUsername(String username);
}
