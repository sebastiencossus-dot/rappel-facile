package com.msprof.Models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Adresses {


    private Integer id;
    private String ville;
    private String rue;
    private String numero;
    private String codepostal;
}

