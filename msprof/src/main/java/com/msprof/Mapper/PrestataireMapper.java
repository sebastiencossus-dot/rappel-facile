package com.msprof.Mapper;


import com.msprof.Models.PrestataireDTO;
import com.msprof.Models.PrestataireRequestDTO;

public class PrestataireMapper {

    public static PrestataireDTO toJpa(PrestataireRequestDTO req) {

        PrestataireDTO dto = new PrestataireDTO();

        dto.setNom(req.getNom());
        dto.setPrenom(req.getPrenom());
        dto.setProfessionIds(req.getProfessionIds());
        dto.setAdresses(req.getAdresses());

        return dto;
    }
}