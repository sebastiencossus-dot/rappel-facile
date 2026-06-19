package com.msadmin.models;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdminRdvDTO {
    private Integer id;
    private LocalDateTime dateRdv;
    private Integer isOK;
    private String userEmail;
    private String prestataireNom;
    private String prestatairePrenom;
}