package com.zzbbc.spring.core.repositories.impl;

import java.util.UUID;
import javax.persistence.EntityManager;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.repositories.UserRepository;

public class UserRepositoryImpl extends BaseRepositoryImpl<UUID, User> implements UserRepository {

    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }
}
