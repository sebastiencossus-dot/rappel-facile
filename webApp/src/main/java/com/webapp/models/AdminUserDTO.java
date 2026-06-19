package com.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdminUserDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
}