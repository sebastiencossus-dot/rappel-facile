package com.msrappel.Controllers;

import com.msrappel.Scheduler.RappelScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RappelScheduler rappelScheduler;

    // to test cron manually
    @GetMapping("/test/cron")
    public String triggerCron() {
        rappelScheduler.processRappels();
        return "Cron exécuté — vérifie les logs et ta boîte mail";
    }
}