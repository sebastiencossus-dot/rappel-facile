package com.msjpa.Mapper;

import com.msjpa.models.*;
import java.util.List;
import java.util.stream.Collectors;

public class PrestataireMapper {

    public static PrestataireResponseDTO toDTO(Prestataires p) {

        PrestataireResponseDTO dto = new PrestataireResponseDTO();

        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setPrenom(p.getPrenom());

        dto.setProfessions(
                p.getExercices().stream()
                        .map(ex -> ex.getProfessions().getNom())
                        .collect(Collectors.toList())
        );

        // ← ajout catégories (dédoublonnées)
        dto.setCategories(
                p.getExercices().stream()
                        .map(ex -> ex.getProfessions().getCategorie())
                        .filter(cat -> cat != null)
                        .map(Categories::getName)
                        .distinct()
                        .collect(Collectors.toList())
        );


        List<AdresseDTO> adresses = p.getLocals().stream()
                .map(l -> {
                    Adresses a = l.getAdresses();
                    return new AdresseDTO(a.getId(), a.getRue(), a.getNumero(), a.getVille(), a.getCodepostal());
                })
                .collect(Collectors.toList());

        dto.setAdresses(adresses);

        return dto;
    }
}