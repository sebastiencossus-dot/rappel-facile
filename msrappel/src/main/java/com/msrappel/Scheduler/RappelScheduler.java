package com.msrappel.Scheduler;



import com.msrappel.Models.RappelDTO;
import com.msrappel.Services.MsJpaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RappelScheduler {

    private final MsJpaClient msJpaClient;
    private final com.msrappel.services.EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(RappelScheduler.class);

    // ✅ s'exécute toutes les minutes
    @Scheduled(cron = "0 * * * * *")
    public void processRappels() {
        log.info("Cron rappels — vérification à {}", LocalDateTime.now());

        try {
            List<RappelDTO> rappels = msJpaClient.findAllRappels();
            LocalDateTime now = LocalDateTime.now();

            for (RappelDTO rappel : rappels) {
                if (rappel.getDateRdv() == null || rappel.getUserEmail() == null) continue;

                // Moment où l'alerte doit partir
                LocalDateTime alerteAt = rappel.getDateRdv()
                        .minusMinutes(rappel.getDelai());


                if (!alerteAt.isBefore(now.minusMinutes(1)) && alerteAt.isBefore(now)) {
                    log.info("Envoi rappel rdvId={} à {}", rappel.getRdvId(), rappel.getUserEmail());
                    emailService.sendRappelEmail(rappel);
                }
            }
        } catch (Exception e) {
            log.error("Erreur cron rappels : {}", e.getMessage());
        }
    }
}

