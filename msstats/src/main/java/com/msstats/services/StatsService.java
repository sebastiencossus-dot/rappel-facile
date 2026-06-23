package com.msstats.services;

import com.msstats.models.*;
import com.msstats.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final ConnexionRepository connexionRepository;
    private final VisiteRepository visiteRepository;
    private final RdvStatRepository rdvStatRepository;

    // ✅ Enregistrer une connexion
    public void recordConnexion(ConnexionRequest request) {
        ConnexionEvent event = new ConnexionEvent();
        event.setUserEmail(request.getUserEmail());
        event.setIpAddress(request.getIpAddress());
        event.setTimestamp(LocalDateTime.now());
        connexionRepository.save(event);
    }

    // ✅ Enregistrer une visite
    public void recordVisite(VisiteRequest request) {
        VisiteEvent event = new VisiteEvent();
        event.setUserEmail(request.getUserEmail());
        event.setPage(request.getPage());
        event.setTimestamp(LocalDateTime.now());
        visiteRepository.save(event);
    }

    // ✅ Enregistrer stats RDV
    public void recordRdvStat(RdvStatRequest request) {
        RdvStatEvent event = new RdvStatEvent();
        event.setUserEmail(request.getUserEmail());
        event.setRdvId(request.getRdvId());
        event.setNbAlertes(request.getNbAlertes());
        event.setTimestamp(LocalDateTime.now());
        rdvStatRepository.save(event);
    }

    // ✅ Dashboard complet
    public DashboardStatsDTO getDashboard() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // Connexions du jour
        long connexionsDuJour = connexionRepository
                .countByTimestampBetween(startOfDay, endOfDay);

        // Visites du jour
        long visitesDuJour = visiteRepository
                .countByTimestampBetween(startOfDay, endOfDay);

        // Total connexions
        long totalConnexions = connexionRepository.count();

        // Connexions par heure (chart linéaire)
        Map<Integer, Long> connexionsParHeure = connexionRepository
                .findByTimestampBetween(startOfDay, endOfDay)
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTimestamp().getHour(),
                        Collectors.counting()
                ));

        // Visites par page (chart donut)
        Map<String, Long> visitesParPage = visiteRepository
                .findByTimestampBetween(startOfDay, endOfDay)
                .stream()
                .collect(Collectors.groupingBy(
                        VisiteEvent::getPage,
                        Collectors.counting()
                ));

        // RDV par user (chart barre)
        Map<String, Long> rdvParUser = rdvStatRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        RdvStatEvent::getUserEmail,
                        Collectors.counting()
                ));

        // Alertes par RDV (chart barre)
        Map<Integer, Integer> alertesParRdv = rdvStatRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        RdvStatEvent::getRdvId,
                        RdvStatEvent::getNbAlertes,
                        (a, b) -> a // garder le premier en cas de doublon
                ));

        return new DashboardStatsDTO(
                connexionsDuJour,
                visitesDuJour,
                totalConnexions,
                connexionsParHeure,
                visitesParPage,
                rdvParUser,
                alertesParRdv
        );
    }
}