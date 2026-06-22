package com.msjpa.controllers;

import com.msjpa.models.*;
import com.msjpa.repositories.CategorieRepository;
import com.msjpa.repositories.ProfessionRepository;
import com.msjpa.services.ProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professions")
@RequiredArgsConstructor
public class ProfessionController {

    private final ProfessionService professionService;
    private final ProfessionRepository professionRepository;
    private final CategorieRepository categorieRepository;

    @GetMapping
    public List<Professions> getAll() {
        return professionService.findAll();
    }

    @GetMapping("/{id}")
    public Professions getById(@PathVariable int id) {
        return professionService.findById(id);
    }


    @GetMapping("/admin")
    public List<ProfessionDTO> getAllAdmin() {
        return professionRepository.findAll().stream()
                .map(p -> new ProfessionDTO(
                        p.getId(),
                        p.getNom(),
                        p.getIsValide(),
                        p.getCategorie() != null ? p.getCategorie().getId() : null,
                        p.getCategorie() != null ? p.getCategorie().getName() : null
                ))
                .toList();
    }


    @PostMapping("/admin")
    public ProfessionDTO create(@RequestBody ProfessionDTO dto) {
        Professions p = new Professions();
        p.setNom(dto.getNom());
        p.setIsValide(dto.getIsValide() != null ? dto.getIsValide() : 1);

        if (dto.getCategorieId() != null) {
            Categories cat = categorieRepository.findById(dto.getCategorieId())
                    .orElseThrow(() -> new RuntimeException("Catégorie introuvable : " + dto.getCategorieId()));
            p.setCategorie(cat);
        }

        Professions saved = professionRepository.save(p);
        return new ProfessionDTO(
                saved.getId(),
                saved.getNom(),
                saved.getIsValide(),
                saved.getCategorie() != null ? saved.getCategorie().getId() : null,
                saved.getCategorie() != null ? saved.getCategorie().getName() : null
        );
    }


    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Integer id) {
        Professions p = professionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profession introuvable : " + id));

        // Vérifier qu'aucun RDV ou prestataire n'utilise cette profession
        if (p.getRdvs() != null && !p.getRdvs().isEmpty()) {
            throw new RuntimeException("Impossible : cette profession est liée à des RDV");
        }
        if (p.getExercices() != null && !p.getExercices().isEmpty()) {
            throw new RuntimeException("Impossible : cette profession est liée à des prestataires");
        }

        professionRepository.deleteById(id);
    }
}