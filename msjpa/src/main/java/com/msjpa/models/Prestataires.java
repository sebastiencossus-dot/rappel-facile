package com.msjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "prestataires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestataires {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private Boolean isValide;


    @OneToMany(mappedBy = "prestataires", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Exerce> exercices;

    @OneToMany(mappedBy = "prestataires", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Local>  locals;


}
