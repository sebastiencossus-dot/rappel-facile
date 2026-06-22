package com.msadmin.controllers;

import com.msadmin.models.CategorieDTO;
import com.msadmin.models.ProfessionDTO;
import com.msadmin.services.AdminProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/professions")
@RequiredArgsConstructor
public class AdminProfessionController {

    private final AdminProfessionService adminProfessionService;

    @GetMapping
    public List<ProfessionDTO> findAll() {
        return adminProfessionService.findAllProfessions();
    }

    @PostMapping
    public ProfessionDTO create(@RequestBody ProfessionDTO dto) {
        return adminProfessionService.createProfession(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminProfessionService.deleteProfession(id);
    }

    @GetMapping("/categories")
    public List<CategorieDTO> findAllCategories() {
        return adminProfessionService.findAllCategories();
    }

    @PostMapping("/categories")
    public CategorieDTO createCategorie(@RequestBody CategorieDTO dto) {
        return adminProfessionService.createCategorie(dto);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategorie(@PathVariable Integer id) {
        adminProfessionService.deleteCategorie(id);
    }
}