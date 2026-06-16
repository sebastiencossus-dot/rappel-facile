package com.msjpa.services;

import com.msjpa.models.RDV;
import com.msjpa.repositories.ChartsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChartsService {

    private final ChartsRepository chartsRepository;

    public ChartsService(ChartsRepository chartsRepository) {
        this.chartsRepository = chartsRepository;
    }

    public Map<String, List<Integer>> getStats(Integer idUser, int annee) {

        List<Object[]> results = chartsRepository.callSpRdvList(idUser, annee);

        List<Integer> medical       = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> beaute        = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> soin          = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> administratif = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> effectue      = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> rate          = new ArrayList<>(Collections.nCopies(12, 0));
        List<Integer> avenir        = new ArrayList<>(Collections.nCopies(12, 0));

        for (Object[] row : results) {
            int mois  = ((Number) row[0]).intValue();
            int index = mois - 1;

            if (index >= 0 && index < 12) {
                medical.set(index,       ((Number) row[1]).intValue());
                beaute.set(index,        ((Number) row[2]).intValue());
                soin.set(index,          ((Number) row[3]).intValue());
                administratif.set(index, ((Number) row[4]).intValue());
                effectue.set(index,      ((Number) row[5]).intValue());
                rate.set(index,          ((Number) row[6]).intValue());
                avenir.set(index,        ((Number) row[7]).intValue());
            }
        }

        Map<String, List<Integer>> result = new LinkedHashMap<>();
        result.put("medical",        medical);
        result.put("beaute",         beaute);
        result.put("soin",           soin);
        result.put("administratif",  administratif);
        result.put("effectue",       effectue);
        result.put("rate",           rate);
        result.put("avenir",         avenir);
        return result;
    }

//    public List<RDV> getDetails(int mois, String type, String valeur) {
//
//        if (type.equals("categorie")) {
//            return rdvRepository.findByCategorieAndMonth(valeur, mois);
//        } else {
//            return rdvRepository.findByStatutAndMonth(valeur, mois);
//        }
//    }
}