package com.msprof.Models;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrestataireRequestDTO {

    private String nom;
    private String prenom;

    private List<Integer> professionIds;

    private List<AdresseDTO> adresses;
}