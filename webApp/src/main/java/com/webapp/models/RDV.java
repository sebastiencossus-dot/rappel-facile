package com.webapp.models;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RDV {
    private Integer id;
    private LocalDateTime dateRdv;
    private Integer isOK;
    private String motif;

    private Integer prestataireId;
    private String prestataireNom;
    private String prestatairePrenom;

    private Integer professionId;
    private String professionNom;

    private Integer adresseId;
    private String adresseNumero;
    private String adresseRue;
    private String adresseVille;
    private String adresseCodePostal;

    private Integer userId;

    private List<RappelDTO> rappels;
}