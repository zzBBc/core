package com.zzbbc.spring.core.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.zzbbc.spring.core.models.BaseModel;

@NoRepositoryBean
public interface BaseRepository<M extends BaseModel<?>, ID> extends ReactiveCrudRepository<M, ID> {

}
