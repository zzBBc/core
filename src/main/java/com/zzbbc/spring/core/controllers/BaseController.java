package com.zzbbc.spring.core.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.BaseResponse;
import com.zzbbc.spring.core.services.BaseService;
import com.zzbbc.spring.core.tasks.MdcCallable;

@RestController
public abstract class BaseController<S extends BaseService<?, ID, ?, DTO>, ID, DTO extends BaseDto<?>> {
    protected final S service;

    @Autowired
    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public MdcCallable<?> findAll(@RequestParam(required = false) Map<String, String> params) {
        return new MdcCallable<>(() -> {
            List<DTO> dtos = this.service.findAll(params);

            return ResponseEntity.ok(new BaseResponse(dtos));
        });
    }

    @GetMapping("/{id}")
    public MdcCallable<?> findById(@PathVariable(name = "id") String id) {
        return new MdcCallable<>(() -> {
        Optional<DTO> dto = this.service.findById(toId(id));
            return ResponseEntity.ok(new BaseResponse(dto));
        });
    }

    protected abstract ID toId(String id);
}
