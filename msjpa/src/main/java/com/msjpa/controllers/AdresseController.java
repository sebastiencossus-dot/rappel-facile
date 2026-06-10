package com.msjpa.controllers;


import com.msjpa.models.Adresses;
import com.msjpa.models.Professions;
import com.msjpa.services.AdresseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
public class AdresseController {

    private final AdresseService adresseService;

    public AdresseController(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @GetMapping
    public List<Adresses> getAll() {
        return adresseService.findAll();
    }

    @GetMapping("/{id}")
    public Adresses getById(@PathVariable int id) {
        return adresseService.findById(id);
    }
}