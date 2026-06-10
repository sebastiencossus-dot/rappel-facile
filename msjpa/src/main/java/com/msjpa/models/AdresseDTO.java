package com.msjpa.models;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdresseDTO {

    private Integer id;
    private String rue;
    private String numero;
    private String ville;
    private String codepostal;
}