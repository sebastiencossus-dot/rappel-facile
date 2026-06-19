package com.msjpa.controllers;


import com.msjpa.models.RappelDTO;
import com.msjpa.repositories.RappelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rappels")
@RequiredArgsConstructor
public class RappelsController {

    private final RappelsRepository rappelsRepository;

    @GetMapping
    public List<RappelDTO> findAll() {
        return rappelsRepository.findAll().stream()
                .map(r -> {
                    RappelDTO dto = new RappelDTO();
                    dto.setId(r.getId());
                    dto.setDelai(r.getDelai());
                    dto.setTypeAlerte(r.getTypeAlerte());
                    dto.setRdvId(r.getRdv().getId());
                    dto.setDateRdv(r.getRdv().getDateRdv());
                    dto.setUserEmail(r.getRdv().getUser().getEmail());
                    dto.setMotif(r.getRdv().getMotif());
                    if (r.getRdv().getPrestataires() != null) {
                        dto.setPrestataireNom(r.getRdv().getPrestataires().getNom());
                        dto.setPrestatairePrenom(r.getRdv().getPrestataires().getPrenom());
                    }
                    return dto;
                })
                .toList();
    }

    @GetMapping("/by-rdv")
    public List<RappelDTO> findByRdvId(@RequestParam Integer rdvId) {
        return rappelsRepository.findByRdvId(rdvId).stream()
                .map(r -> {
                    RappelDTO dto = new RappelDTO();
                    dto.setId(r.getId());
                    dto.setDelai(r.getDelai());
                    dto.setTypeAlerte(r.getTypeAlerte());
                    dto.setRdvId(r.getRdv().getId());
                    return dto;
                })
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteRappel(@PathVariable Integer id) {
        rappelsRepository.deleteById(id);
    }
}