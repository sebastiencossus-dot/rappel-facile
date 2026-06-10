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
@Table(name = "professions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private Integer isValide;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categorie;

    @OneToMany(mappedBy = "professions")
    @JsonIgnore
    private List<RDV> rdvs;

    @OneToMany(mappedBy = "professions")
    @JsonIgnore
    private List<Exerce> exercices;
}
