package com.webapp.services;

import com.webapp.models.CategorieDTO;
import com.webapp.models.ProfessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProfessionService {

    private final MsAdminClient msAdminClient;

    public List<ProfessionDTO> findAllProfessions() {
        return msAdminClient.findAllProfessions();
    }

    public void createProfession(ProfessionDTO dto) {
        msAdminClient.createProfession(dto);
    }

    public void deleteProfession(Integer id) {
        msAdminClient.deleteProfession(id);
    }

    public List<CategorieDTO> findAllCategories() {
        return msAdminClient.findAllCategories();
    }

    public void createCategorie(CategorieDTO dto) {
        msAdminClient.createCategorie(dto);
    }

    public void deleteCategorie(Integer id) {
        msAdminClient.deleteCategorie(id);
    }
}