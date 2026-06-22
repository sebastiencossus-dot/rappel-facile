package com.msadmin.services;

import com.msadmin.services.MsJpaClient;
import com.msadmin.models.CategorieDTO;
import com.msadmin.models.ProfessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProfessionService {

    private final MsJpaClient msJpaClient;

    public List<ProfessionDTO> findAllProfessions() {
        return msJpaClient.getAllProfessionsAdmin();
    }

    public ProfessionDTO createProfession(ProfessionDTO dto) {
        return msJpaClient.createProfession(dto);
    }

    public void deleteProfession(Integer id) {
        msJpaClient.deleteProfession(id);
    }

    public List<CategorieDTO> findAllCategories() {
        return msJpaClient.getAllCategoriesAdmin();
    }

    public CategorieDTO createCategorie(CategorieDTO dto) {
        return msJpaClient.createCategorie(dto);
    }

    public void deleteCategorie(Integer id) {
        msJpaClient.deleteCategorie(id);
    }
}