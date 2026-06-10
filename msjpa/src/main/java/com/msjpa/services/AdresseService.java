package com.msjpa.services;


import com.msjpa.models.Adresses;
import com.msjpa.repositories.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;

    private final AdresseRepository repository;

    public AdresseService(AdresseRepository repository) {
        this.repository = repository;
    }

    public List<Adresses> findAll() {
        return repository.findAll();
    }

    public Adresses findById(Integer id) {
        return adresseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse introuvable"));
    }

}
