// DashboardStatsDTO.java
package com.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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