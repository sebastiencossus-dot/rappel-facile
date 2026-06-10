package com.msprof.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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