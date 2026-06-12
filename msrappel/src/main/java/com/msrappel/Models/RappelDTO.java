package com.msrappel.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RappelDTO {
    private Integer id;
    private Integer delai;
    private String typeAlerte;
    private Integer rdvId;
    private LocalDateTime dateRdv;
    private String userEmail;
    private String motif;
    private String prestataireNom;
    private String prestatairePrenom;
}