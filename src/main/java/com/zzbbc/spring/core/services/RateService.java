package com.zzbbc.spring.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzbbc.spring.core.models.Rate;
import com.zzbbc.spring.core.repositories.RateRepository;

@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    public Rate getRateByType(String type) {
        return repository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Rate Not Found: " + type));
    }
}
