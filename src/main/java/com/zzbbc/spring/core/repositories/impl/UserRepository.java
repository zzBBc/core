package com.zzbbc.spring.core.repositories.impl;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.repositories.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

}
