package com.msprof.Models;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Professions {

    private Integer id;
    private String nom;
    private Boolean isValide;

    private Categories categories;
}
