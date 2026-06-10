package com.msprof.Models;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdresseDTO {

    private String rue;
    private String numero;
    private String ville;
    private String codepostal;
}