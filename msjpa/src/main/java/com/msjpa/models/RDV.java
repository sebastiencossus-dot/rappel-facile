package com.msjpa.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "rdv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RDV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private LocalDateTime dateRdv;
    private Integer isOK;
    private String motif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestataires_id")
    private Prestataires prestataires;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "adresses_id")
    private Adresses adresses;

    @ManyToOne
    @JoinColumn(name = "professions_id")
    private Professions professions;

    @OneToMany(mappedBy = "rdv")
    private List<Rappels> rappels;



    public Professions getProfessions() {
        return professions;
    }

    public void setProfessions(Professions professions) {
        this.professions = professions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Adresses getAdresses() {
        return adresses;
    }

    public void setAdresses(Adresses adresses) {
        this.adresses = adresses;
    }

    public Prestataires getPrestataires() {
        return prestataires;
    }

    public void setPrestataires(Prestataires prestataires) {
        this.prestataires = prestataires;
    }


}