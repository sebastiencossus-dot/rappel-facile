package com.msadmin.models;

import lombok.*;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DashboardStatsDTO {
    private long connexionsDuJour;
    private long visitesDuJour;
    private long totalConnexions;
    private Map<Integer, Long> connexionsParHeure;
    private Map<String, Long> visitesParPage;
    private Map<String, Long> rdvParUser;
    private Map<Integer, Integer> alertesParRdv;
}