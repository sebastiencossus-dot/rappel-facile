package com.msprof.Mapper;


import com.msprof.Models.PrestataireDTO;
import com.msprof.Models.PrestataireRequestDTO;
import com.msprof.Models.PrestataireUpdateDTO;

public class PrestataireMapper {

    public static PrestataireDTO toJpa(PrestataireRequestDTO req) {

        PrestataireDTO dto = new PrestataireDTO();

        dto.setNom(req.getNom());
        dto.setPrenom(req.getPrenom());
        dto.setProfessionIds(req.getProfessionIds());
        dto.setAdresses(req.getAdresses());

        return dto;
    }

    public static PrestataireUpdateDTO toJpaUpdate(PrestataireRequestDTO req) {

        PrestataireUpdateDTO dto = new PrestataireUpdateDTO();

        dto.setNom(req.getNom());
        dto.setPrenom(req.getPrenom());
        dto.setProfessionIds(req.getProfessionIds());
        dto.setAdresses(req.getAdresses());

        return dto;
    }
}