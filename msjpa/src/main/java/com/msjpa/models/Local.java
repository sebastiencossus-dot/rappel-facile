package com.msjpa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "local")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Local {

    @EmbeddedId
    private LocalId id;

    @ManyToOne
    @MapsId("prestatairesId")
    @JoinColumn(name = "prestataires_id")
    private Prestataires prestataires;

    @ManyToOne
    @MapsId("adressesId")
    @JoinColumn(name = "adresses_id")
    private Adresses adresses;

    @Column(name = "is_valide")
    private boolean isValide;

}
