package com.webapp.services;

import com.webapp.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferentielService {

    private final MsJpaClient msJpaClient;

    @Cacheable("professions")
    public List<Professions> getAllProfessions() {
        return msJpaClient.findAllProfessions();
    }

    @Cacheable("categories")
    public List<Categories> getAllCategories() {
        return msJpaClient.findAllCategories();
    }

    @Cacheable("adresses")
    public List<Adresses> getAllAdresses() {
        return msJpaClient.findAllAdresses();
    }
}