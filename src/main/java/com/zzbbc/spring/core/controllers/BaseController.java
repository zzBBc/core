package com.zzbbc.spring.core.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.services.BaseService;

@RestController
public abstract class BaseController<S extends BaseService<?, ID, ?, DTO>, ID, DTO extends BaseDto<?>> {
    protected final S service;

    @Autowired
    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public Callable<?> findAll() {
        return () -> {
        List<DTO> dtos = this.service.findAll();

            return ResponseEntity.ok(new BaseResponse(dtos));
        };
    }

    @GetMapping("/{id}")
    public Callable<?> findById(@PathVariable(name = "id") String id) {
        return () -> {
        Optional<DTO> dto = this.service.findById(toId(id));
            return ResponseEntity.ok(new BaseResponse(dto));
        };
    }

    protected abstract ID toId(String id);
}
