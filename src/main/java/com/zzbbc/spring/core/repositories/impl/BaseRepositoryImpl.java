package com.zzbbc.spring.core.repositories.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.zzbbc.spring.core.models.BaseModel;
import com.zzbbc.spring.core.repositories.BaseRepository;
import com.zzbbc.spring.core.repositories.predicates.BasePredicate;
import com.zzbbc.spring.core.repositories.predicates.SearchCriteria;
import com.zzbbc.spring.core.validators.impl.CommonValidator;

@NoRepositoryBean
public abstract class BaseRepositoryImpl<ID, M extends BaseModel<?>>
        extends SimpleJpaRepository<M, ID> implements BaseRepository<M, ID> {
    protected final EntityManager entityManager;
    protected final Class<M> domainClass;

    protected BaseRepositoryImpl(Class<M> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    public List<M> findAll(Map<String, String> params) {
        List<SearchCriteria> filters = CommonValidator.validateSearchCriteria(params);

        return predicate(filters);
    }

    public List<M> predicate(List<SearchCriteria> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = initCriteriaQuery(criteriaBuilder);

        BasePredicate<M> predicate = createPredicate(criteriaBuilder, query);
        predicate.setSearchCriterias(filters);
        query.where(predicate.toPredicates());

        return entityManager.createQuery(query).getResultList();
    }

    private BasePredicate<M> createPredicate(CriteriaBuilder criteriaBuilder,
            CriteriaQuery<M> query) {
        Root<M> root = query.from(domainClass);
        query.select(root);

        BasePredicate<M> predicate = new BasePredicate<M>(domainClass, root, criteriaBuilder);
        return predicate;
    }

    private CriteriaQuery<M> initCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.createQuery(domainClass);
    }
}
