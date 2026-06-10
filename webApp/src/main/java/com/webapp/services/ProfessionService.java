package com.webapp.services;

import com.webapp.models.Professions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {

    private final ProfessionClient client;

    public ProfessionService(ProfessionClient client) {
        this.client = client;
    }

    public List<Professions> findAll() {
        return client.findAll();
    }

    public Professions findById(Integer id) {
        return client.findById(id);
    }
}
