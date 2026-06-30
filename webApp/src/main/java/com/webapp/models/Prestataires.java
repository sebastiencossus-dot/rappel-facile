package com.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Prestataires {


    private Integer id;
    private String nom;
    private String prenom;
    private Boolean isValide;

    private List<Exerce> exercices;

    private List<Local>  locals;






}