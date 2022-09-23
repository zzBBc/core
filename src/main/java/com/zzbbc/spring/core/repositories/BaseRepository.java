package com.zzbbc.spring.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.zzbbc.spring.core.models.BaseModel;

@NoRepositoryBean
public interface BaseRepository<M extends BaseModel<?>, ID> extends JpaRepository<M, ID> {

}
