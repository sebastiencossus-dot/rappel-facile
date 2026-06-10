package com.msjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "rappels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rappels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer delai;
    private String typeAlerte;

    @ManyToOne
    @JoinColumn(name = "rdv_id")
    private RDV rdv;

}
