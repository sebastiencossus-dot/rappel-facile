package com.msjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "adresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ville;
    private String rue;
    private String numero;
    private String codepostal;

    @OneToMany(mappedBy = "adresses")
    @JsonIgnore
    private List<RDV> rdvs;

    @OneToMany(mappedBy = "adresses")
    @JsonIgnore
    private List<Local> localises;


}
