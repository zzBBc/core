package com.zzbbc.spring.core.repositories.impl;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.EntityManager;
import com.zzbbc.spring.core.enums.FilterOperator;
import com.zzbbc.spring.core.enums.RoleEnum;
import com.zzbbc.spring.core.models.impl.Role;
import com.zzbbc.spring.core.repositories.RoleRepository;
import com.zzbbc.spring.core.repositories.predicates.SearchCriteria;

public class RoleRepositoryImpl extends BaseRepositoryImpl<UUID, Role> implements RoleRepository {

    protected RoleRepositoryImpl(EntityManager entityManager) {
        super(Role.class, entityManager);
    }

    @Override
    public Role getOrInsert(RoleEnum roleEnum) {
        Role role = this.findByName(roleEnum);

        if (Objects.nonNull(role)) {
            return role;
        }

        role = new Role(roleEnum);

        return this.save(role);
    }

    private Role findByName(RoleEnum roleEnum) {
        return this.findOne(new SearchCriteria("name", FilterOperator.EQUALS, roleEnum.name()));
    }

}
