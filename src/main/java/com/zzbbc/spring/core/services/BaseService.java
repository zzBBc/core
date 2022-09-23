package com.zzbbc.spring.core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.BaseModel;
import com.zzbbc.spring.core.repositories.BaseRepository;

public abstract class BaseService<R extends BaseRepository<M, ID>, ID, M extends BaseModel<DTO>, DTO extends BaseDto<M>> {
    protected final R repository;

    @Autowired
    public BaseService(R repository) {
        this.repository = repository;
    }

    public List<DTO> findAll() {
        return repository.findAll().stream().map(M::toDto).collect(Collectors.toList());
    }

    public Page<DTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(M::toDto);
    }

    public Optional<DTO> findById(ID id) {
        Optional<M> model = this.repository.findById(id);

        return model.map(M::toDto);
    }

    public M saveModel(M model) {
        return repository.save(model);
    }
}
