package com.msjpa.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProfessionDTO {
    private Integer id;
    private String nom;
    private Integer isValide;
    private Integer categorieId;
    private String categorieNom;
}