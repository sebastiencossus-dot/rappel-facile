package com.msprof.Models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Prestataires{

    private Integer id;
    private String nom;
    private String prenom;
    private Boolean isValide;

    private Professions profession;
    private Adresses adresse;
}





