package com.msadmin.controllers;

import com.msadmin.models.AdminRdvDTO;
import com.msadmin.services.AdminRdvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rdv")
@RequiredArgsConstructor
public class AdminRdvController {

    private final AdminRdvService adminRdvService;

    @GetMapping
    public List<AdminRdvDTO> findAll() {
        return adminRdvService.findAll();
    }

    @GetMapping("/statut/{statut}")
    public List<AdminRdvDTO> findByStatut(@PathVariable Integer statut) {
        return adminRdvService.findByStatut(statut);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminRdvService.delete(id);
    }
}