package com.msjpa.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exerce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exerce {

    @EmbeddedId
    private ExerceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("prestatairesId")
    @JoinColumn(name = "prestataires_id")
    private Prestataires prestataires;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professionsId")
    @JoinColumn(name = "professions_id")
    private Professions professions;

    @Column(name = "is_valide")
    private boolean isValide;
}
