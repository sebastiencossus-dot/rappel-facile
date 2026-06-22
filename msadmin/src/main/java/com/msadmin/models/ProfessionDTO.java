package com.msadmin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProfessionDTO {
    private Integer id;
    private String nom;
    private Integer isValide;
    private Integer categorieId;
    private String categorieNom;
}