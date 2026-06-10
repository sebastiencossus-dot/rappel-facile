package com.msRdv.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Professions {

    private Integer id;
    private String nom;
    private Integer isValide;

    private Categories categories;
}
