package com.zzbbc.spring.core.controllers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.dtos.BaseDto;
import com.zzbbc.spring.core.models.BasePage;
import com.zzbbc.spring.core.models.BasePageRequest;
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
            Pageable pageable = this.getPageable(params);

            if (Objects.nonNull(pageable)) {
                BasePage<DTO> dtos = this.service.findAll(params, pageable);
                return BaseResponse.success(dtos);
            } else {
                List<DTO> dtos = this.service.findAll(params);
                return BaseResponse.success(dtos);
            }
        });
    }

    @GetMapping("/{id}")
    public MdcCallable<?> findById(@PathVariable(name = "id") String id) {
        return new MdcCallable<>(() -> {
        Optional<DTO> dto = this.service.findById(toId(id));
            return BaseResponse.success(dto);
        });
    }

    private Pageable getPageable(Map<String, String> params) {
        String pageIndex = params.remove("pageIndex");
        String pageSize = params.remove("pageSize");

        try {
            Integer page = Integer.parseInt(pageIndex);
            if (page == 0) {
                page = 1;
            }
            return BasePageRequest.of(page, Integer.parseInt(pageSize));
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract ID toId(String id);
}
