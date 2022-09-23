package com.zzbbc.spring.core.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.zzbbc.spring.core.dto.BaseDto;
import com.zzbbc.spring.core.services.BaseService;

public abstract class BaseController<S extends BaseService<?, ID, ?, DTO>, ID, DTO extends BaseDto<?>> {
    protected final S service;

    @Autowired
    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<DTO> dtos = this.service.findAll();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") String id) {
        Optional<DTO> dto = this.service.findById(toId(id));

        return ResponseEntity.of(dto);
    }

    protected abstract ID toId(String id);
}
