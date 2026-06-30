package com.webapp.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exerce {


    private ExerceId id;


    private Prestataires prestataires;


    private Professions professions;

    private boolean isValide;
}
