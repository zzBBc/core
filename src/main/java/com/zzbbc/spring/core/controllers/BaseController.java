package com.zzbbc.spring.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.services.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public abstract class BaseController<S extends BaseService<?, ID, ?, DTO>, ID, DTO extends BaseDto<?>> {
    protected final S service;

    @Autowired
    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public Flux<?> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<?> findById(@PathVariable(name = "id") String id) {
        return this.service.findById(toId(id));
    }

    protected abstract ID toId(String id);
}
