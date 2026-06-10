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
public class PrestataireDTO {

    private String nom;
    private String prenom;

    private List<Integer> professionIds;

    private List<AdresseDTO> adresses;


}
