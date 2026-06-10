package com.webapp.services;

import com.webapp.models.Prestataires;
import com.webapp.models.RdvPrestDTO;
import com.webapp.models.User;
import com.webapp.models.RDV;
import com.webapp.services.form.rdvForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RdvService {

    @Autowired
    MsJpaClient msJpaClient;

    @Autowired
    SessionService sessionService;

    @Autowired
    PrestataireService prestataireService;

    @Autowired
    ProfessionService professionService;

    @Autowired
    AdresseService adresseService;

    public RdvService(MsJpaClient msJpaClient) {
        this.msJpaClient = msJpaClient;
    }

    public List<RDV> getRdvByUser(@RequestBody String email) {
        return msJpaClient.getRdvByUser(email);
    }

    public RDV createRdv(rdvForm form) {
        RdvPrestDTO dto = new RdvPrestDTO();
        dto.setDateRdv(form.getDateRdv());
        dto.setMotif(form.getMotif());
        dto.setPrestataireId(form.getPrestataireId());
        dto.setAdresseId(form.getAdresseId());
        dto.setProfessionId(form.getProfessionId());

        User user = sessionService.sessionUser();
        dto.setUserId(user.getIdUser());  // ← idUser de webapp

        return msJpaClient.createRdv(dto);
    }

    public RDV updateRdv(Integer id, RDV rdv) {
        User user = sessionService.sessionUser();
        return msJpaClient.updateRdv(id, rdv, user.getEmail());
    }

    public void deleteRdv(Integer id) {
        User user = sessionService.sessionUser();
        msJpaClient.deleteRdv(id, user.getEmail());
    }
}
