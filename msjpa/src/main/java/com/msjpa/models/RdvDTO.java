package com.msjpa.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RdvDTO {
    private Integer id;
    private LocalDateTime dateRdv;
    private Integer isOK;
    private String motif;

    // Prestataire
    private Integer prestataireId;
    private String prestataireNom;
    private String prestatairePrenom;

    // Profession
    private Integer professionId;
    private String professionNom;

    // Adresse
    private Integer adresseId;
    private String adresseNumero;
    private String adresseRue;
    private String adresseVille;
    private String adresseCodePostal;

    // User
    private Integer userId;

    // Rappels
    private List<RappelDTO> rappels;
}