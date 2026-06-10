package com.msjpa.controllers;

import com.msjpa.models.Professions;
import com.msjpa.services.ProfessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public List<Professions> getAll() {
        return professionService.findAll();
    }

    @GetMapping("/{id}")
    public Professions getById(@PathVariable int id) {
        return professionService.findById(id);
    }
}