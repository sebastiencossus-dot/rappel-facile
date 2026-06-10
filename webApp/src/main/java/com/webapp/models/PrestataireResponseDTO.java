package com.webapp.models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestataireResponseDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private List<String> professions;
    private List<String> categories;
    private List<AdresseDTO> adresses;
}