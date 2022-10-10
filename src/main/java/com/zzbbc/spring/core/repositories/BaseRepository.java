package com.zzbbc.spring.core.repositories;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.zzbbc.spring.core.models.BasePage;
import com.zzbbc.spring.core.models.Model;
import com.zzbbc.spring.core.repositories.predicates.SearchCriteria;

@NoRepositoryBean
public interface BaseRepository<ID, M extends Model<?>> extends JpaRepository<M, ID> {
    List<M> findAll(Map<String, String> params);

    BasePage<M> findAll(Map<String, String> params, Pageable pageable);

    M findOne(SearchCriteria searchCriteria);
}
