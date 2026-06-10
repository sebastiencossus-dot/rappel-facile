package com.msjpa.services;


import com.msjpa.models.Professions;
import com.msjpa.repositories.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {

    @Autowired
    private ProfessionRepository professionRepository;

    private final ProfessionRepository repository;

    public ProfessionService(ProfessionRepository repository) {
        this.repository = repository;
    }

    public List<Professions> findAll() {
        return repository.findAll();
    }

    public Professions findById(Integer id) {
        return professionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profession introuvable"));
    }

}
