package com.msadmin.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdminUserDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
}