package com.msjpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LocalId implements Serializable {

    @Column(name = "prestataires_id")
    private Integer prestatairesId;

    @Column(name = "adresses_id")
    private Integer adressesId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocalId localId = (LocalId) o;
        return Objects.equals(prestatairesId, localId.prestatairesId) && Objects.equals(adressesId, localId.adressesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prestatairesId, adressesId);
    }

    public Integer getPrestatairesId() { return prestatairesId; }
    public void setPrestatairesId(Integer id) { this.prestatairesId = id; }
    public Integer getAdressesId() { return adressesId; }
    public void setAdressesId(Integer id) { this.adressesId = id; }
}
