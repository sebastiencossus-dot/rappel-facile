package com.msjpa.controllers;

import com.msjpa.models.CategorieDTO;
import com.msjpa.models.Categories;
import com.msjpa.repositories.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategorieRepository categorieRepository;

    @GetMapping
    public List<Categories> getAll() {
        return categorieRepository.findAll();
    }

    @GetMapping("/admin")
    public List<CategorieDTO> getAllAdmin() {
        return categorieRepository.findAll().stream()
                .map(c -> new CategorieDTO(c.getId(), c.getName()))
                .toList();
    }

    @PostMapping("/admin")
    public CategorieDTO create(@RequestBody CategorieDTO dto) {
        Categories cat = new Categories();
        cat.setName(dto.getName());
        Categories saved = categorieRepository.save(cat);
        return new CategorieDTO(saved.getId(), saved.getName());
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Integer id) {
        Categories cat = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable : " + id));
        if (cat.getProfessions() != null && !cat.getProfessions().isEmpty()) {
            throw new RuntimeException("Impossible : cette catégorie contient des professions");
        }
        categorieRepository.deleteById(id);
    }
}