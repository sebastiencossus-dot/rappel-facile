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




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Boolean getIsValide() { return isValide; }

    public void setIsValide(Boolean isValide) { this.isValide = isValide; }

    public List<Exerce> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exerce> exercices) {
        this.exercices = exercices;
    }
}
