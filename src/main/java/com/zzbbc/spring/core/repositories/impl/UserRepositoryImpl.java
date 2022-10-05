package com.zzbbc.spring.core.repositories.impl;

import java.util.UUID;
import javax.persistence.EntityManager;
import com.zzbbc.spring.core.enums.FilterOperator;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.repositories.UserRepository;
import com.zzbbc.spring.core.repositories.predicates.SearchCriteria;

public class UserRepositoryImpl extends BaseRepositoryImpl<UUID, User> implements UserRepository {

    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User findByUsername(String username) {
        return this.findOne(new SearchCriteria("username", FilterOperator.EQUALS, username));
    }
}
