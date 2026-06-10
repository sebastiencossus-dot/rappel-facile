package com.msRdv.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RdvDTO {
    private Integer id;
    private LocalDateTime dateRdv;
    private String prestataireNom;
    private String prestatairePrenom;
    private Integer userId;
    private Integer prestataireId;
    private Integer adresseId;
    private Integer professionId;

}
