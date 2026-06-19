package com.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RappelDTO {
    private Integer id;
    private Integer delai;
    private String typeAlerte;

    private Integer rdvId;
}