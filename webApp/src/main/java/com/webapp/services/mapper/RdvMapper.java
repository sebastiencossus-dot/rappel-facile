package com.webapp.services.mapper;

import com.webapp.models.RDV;
import com.webapp.services.form.rdvForm;

public class RdvMapper {

    private RdvMapper() {}

    public static rdvForm toForm(RDV rdv) {
        if (rdv == null) {
            return null;
        }

        rdvForm form = new rdvForm();
        form.setId(rdv.getId());
        form.setDateRdv(rdv.getDateRdv());
        form.setMotif(rdv.getMotif());
        form.setPrestataireId(rdv.getPrestataireId());
        form.setAdresseId(rdv.getAdresseId());
        form.setProfessionId(rdv.getProfessionId());

        return form;
    }
}
