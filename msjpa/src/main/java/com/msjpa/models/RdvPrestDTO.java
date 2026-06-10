package com.msjpa.models;

import java.time.LocalDateTime;

public class RdvPrestDTO {

    private LocalDateTime dateRdv;
    private String motif;
    private Integer userId;
    private Integer prestataireId;
    private Integer adresseId;
    private Integer professionId;

    // getters/setters
    public LocalDateTime getDateRdv() { return dateRdv; }
    public void setDateRdv(LocalDateTime dateRdv) { this.dateRdv = dateRdv; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getPrestataireId() { return prestataireId; }
    public void setPrestataireId(Integer prestataireId) { this.prestataireId = prestataireId; }
    public Integer getAdresseId() { return adresseId; }
    public void setAdresseId(Integer adresseId) { this.adresseId = adresseId; }
    public Integer getProfessionId() { return professionId; }
    public void setProfessionId(Integer professionId) { this.professionId = professionId; }
}