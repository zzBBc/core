package com.zzbbc.spring.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.BaseModel;
import com.zzbbc.spring.core.repositories.BaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseService<R extends BaseRepository<M, ID>, ID, M extends BaseModel<DTO>, DTO extends BaseDto<M>> {
    protected final R repository;

    @Autowired
    public BaseService(R repository) {
        this.repository = repository;
    }

    public Flux<DTO> findAll() {
        return repository.findAll().map(M::toDto);
    }

    public Flux<DTO> findAll(Pageable pageable) {
        Integer skip =
                pageable.getPageNumber() == 0 ? pageable.getPageNumber() * pageable.getPageSize()
                        : (pageable.getPageNumber() - 1) * pageable.getPageSize();
        return repository.findAll().skip(skip).take(pageable.getPageSize()).map(M::toDto);
    }

    public Mono<DTO> findById(ID id) {
        Mono<M> model = this.repository.findById(id);

        return model.map(M::toDto);
    }

    public Mono<M> saveModel(M model) {
        return repository.save(model);
    }
}
