package com.msjpa.models;

import lombok.*;

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

    public List<Integer> getProfessionIds() {
        return professionIds;
    }

    public void setProfessionIds(List<Integer> professionIds) {
        this.professionIds = professionIds;
    }

    public List<AdresseDTO> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<AdresseDTO> adresses) {
        this.adresses = adresses;
    }
}
