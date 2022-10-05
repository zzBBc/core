package com.zzbbc.spring.core.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.zzbbc.spring.core.models.BaseModel;
import com.zzbbc.spring.core.models.BasePage;
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

    public BasePage<M> findAll(Map<String, String> params, Pageable pageable) {
        List<SearchCriteria> filters = CommonValidator.validateSearchCriteria(params);

        return predicate(filters, pageable);
    }

    public M findOne(SearchCriteria searchCriteria) {
        CriteriaQuery<M> criteriaQuery = createCriteriaQuery(new ArrayList<SearchCriteria>() {
            {
                add(searchCriteria);
            }
        });

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private BasePage<M> predicate(List<SearchCriteria> filters, Pageable pageable) {
        CriteriaQuery<M> criteriaQuery = createCriteriaQuery(filters, pageable);

        CriteriaQuery<Long> countQuery = createCriteriaCountQuery(filters, pageable);

        List<M> models =
                entityManager.createQuery(criteriaQuery).setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize()).getResultList();

        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();
        return new BasePage<>(models, pageable, totalElements);
    }

    public List<M> predicate(List<SearchCriteria> filters) {
        CriteriaQuery<M> criteriaQuery = createCriteriaQuery(filters);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private CriteriaQuery<M> createCriteriaQuery(List<SearchCriteria> filters) {
        return createCriteriaQuery(filters, null);
    }

    private CriteriaQuery<Long> createCriteriaCountQuery(List<SearchCriteria> filters,
            Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

        query.select(criteriaBuilder.count(query.from(domainClass)));
        return query;
    }

    private CriteriaQuery<M> createCriteriaQuery(List<SearchCriteria> filters, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = criteriaBuilder.createQuery(domainClass);

        Root<M> root = query.from(domainClass);
        query.select(root);
        BasePredicate<M> predicate = new BasePredicate<M>(domainClass, root, criteriaBuilder);
        predicate.setSearchCriterias(filters);

        query.where(predicate.toPredicates());

        if (Objects.nonNull(pageable) && Objects.nonNull(pageable.getSort())) {
            List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, criteriaBuilder);
            query.orderBy(orders);
        }
        return query;
    }
}
