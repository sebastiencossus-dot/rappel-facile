package com.msprof.controllers;


import com.msprof.Models.PrestataireRequestDTO;
import com.msprof.Models.PrestataireResponseDTO;
import com.msprof.Models.PrestataireDetailDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestataires")
public class PrestataireController {

    private final com.msprof.Services.PrestataireService service;

    public PrestataireController(com.msprof.Services.PrestataireService service) {
        this.service = service;
    }

    @GetMapping
    public List<PrestataireResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PrestataireResponseDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/detail")
    public PrestataireDetailDTO findDetailById(@PathVariable Integer id) {
        return service.findDetailById(id);
    }

    @PostMapping
    public void create(@RequestBody PrestataireRequestDTO request) {
        service.create(request);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody PrestataireRequestDTO request) {
        service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}