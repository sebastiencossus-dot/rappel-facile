// DashboardStatsDTO.java
package com.msstats.models;

import lombok.*;
import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DashboardStatsDTO {
    private long connexionsDuJour;
    private long visitesDuJour;
    private long totalConnexions;
    // Connexions par heure sur les 24h (pour chart)
    private Map<Integer, Long> connexionsParHeure;
    // Visites par page (pour chart)
    private Map<String, Long> visitesParPage;
    // Nb RDV par user (top 10)
    private Map<String, Long> rdvParUser;
    // Nb alertes par RDV
    private Map<Integer, Integer> alertesParRdv;
}