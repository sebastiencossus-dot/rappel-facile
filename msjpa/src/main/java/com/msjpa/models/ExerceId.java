package com.msjpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExerceId implements Serializable {

    @Column(name = "prestataires_id")
    private Integer prestatairesId;

    @Column(name = "professions_id")
    private Integer professionsId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExerceId exerceId = (ExerceId) o;
        return Objects.equals(prestatairesId, exerceId.prestatairesId) && Objects.equals(professionsId, exerceId.professionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prestatairesId, professionsId);
    }

    public Integer getPrestatairesId() { return prestatairesId; }
    public void setPrestatairesId(Integer id) { this.prestatairesId = id; }
    public Integer getProfessionsId() { return professionsId; }
    public void setProfessionsId(Integer id) { this.professionsId = id; }
}