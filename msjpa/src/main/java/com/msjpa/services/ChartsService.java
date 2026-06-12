package com.msjpa.services;

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

            for (Object[] row : results) {
                int mois = ((Number) row[0]).intValue();
                System.out.println("Mois: " + mois + " | medical: " + row[1] + " | beaute: " + row[2] + " | soin: " + row[3] + " | admin: " + row[4]);
                int index = mois - 1;
                if (index >= 0 && index < 12) {
                    medical.set(index,       ((Number) row[1]).intValue());
                    beaute.set(index,        ((Number) row[2]).intValue());
                    soin.set(index,          ((Number) row[3]).intValue());
                    administratif.set(index, ((Number) row[4]).intValue());
                }
            }

            System.out.println("medical final: " + medical);

            Map<String, List<Integer>> result = new LinkedHashMap<>();
            result.put("medical",        medical);
            result.put("beaute",         beaute);
            result.put("soin",           soin);
            result.put("administratif",  administratif);
            return result;
        }
}