package com.msjpa.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrestataireRequestDTO {

    private Integer id;
    private String nom;
    private String prenom;

    private List<String> professions;

    private List<AdresseDTO> adresses;
}