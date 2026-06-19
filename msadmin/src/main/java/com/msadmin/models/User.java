package com.msadmin.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
    private String role;
}