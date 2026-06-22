// com/msadmin/models/AdresseDTO.java
package com.msadmin.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdresseDTO {
    private String numero;
    private String rue;
    private String ville;
    private String codepostal;
}