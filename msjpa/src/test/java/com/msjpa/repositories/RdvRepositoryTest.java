package com.msjpa.repositories;

import com.msjpa.models.Adresses;
import com.msjpa.models.Prestataires;
import com.msjpa.models.Professions;
import com.msjpa.models.RDV;
import com.msjpa.models.Rappels;
import com.msjpa.models.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RdvRepositoryTest {

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private Prestataires prestataire;
    private Professions profession;
    private Adresses adresse;
    private RDV rdv;

    @BeforeEach
    void setUp() {

        // ---------------------------
        // Utilisateur
        // ---------------------------

        user = new User();
        user.setEmail("test@test.fr");
        user.setPassword("password");
        user.setNom("Test");
        user.setPrenom("Utilisateur");

        entityManager.persist(user);

        // ---------------------------
        // Prestataire
        // ---------------------------

        prestataire = new Prestataires();
        prestataire.setNom("Dupont");
        prestataire.setPrenom("Jean");
        prestataire.setIsValide(true);

        entityManager.persist(prestataire);

        // ---------------------------
        // Profession
        // ---------------------------

        profession = new Professions();
        profession.setNom("Médecin");
        profession.setIsValide(1);

        entityManager.persist(profession);

        // ---------------------------
        // Adresse
        // ---------------------------

        adresse = new Adresses();
        adresse.setNumero("10");
        adresse.setRue("Rue de la Paix");
        adresse.setVille("Aix-en-Provence");
        adresse.setCodepostal("13100");

        entityManager.persist(adresse);

        // ---------------------------
        // Rendez-vous
        // ---------------------------

        rdv = new RDV();

        rdv.setDateRdv(
                LocalDateTime.of(
                        2026,
                        10,
                        15,
                        14,
                        30
                )
        );

        rdv.setMotif("Consultation médicale");
        rdv.setIsOK(1);

        rdv.setUser(user);
        rdv.setPrestataires(prestataire);
        rdv.setProfessions(profession);
        rdv.setAdresses(adresse);

        entityManager.persist(rdv);

        // ---------------------------
        // Rappel
        // ---------------------------

        Rappels rappel = new Rappels();
        rappel.setDelai(1440);
        rappel.setTypeAlerte("EMAIL");
        rappel.setRdv(rdv);

        entityManager.persist(rappel);

        /*
         * On force l'écriture dans H2.
         */
        entityManager.flush();

        /*
         * On vide le contexte de persistance.
         *
         * C'est important pour findFullByUserId :
         * nous voulons réellement vérifier que la requête
         * recharge les données depuis la base.
         */
        entityManager.clear();
    }

    // --------------------------------------------------
    // Recherche par e-mail utilisateur
    // --------------------------------------------------

    @Test
    void findByUserEmail_shouldReturnUserAppointments() {

        List<RDV> result =
                rdvRepository.findByUser_Email(
                        "test@test.fr"
                );

        assertNotNull(result);
        assertEquals(1, result.size());

        RDV found = result.get(0);

        assertEquals(
                "Consultation médicale",
                found.getMotif()
        );

        assertEquals(
                "test@test.fr",
                found.getUser().getEmail()
        );
    }

    // --------------------------------------------------
    // Recherche par statut
    // --------------------------------------------------

    @Test
    void findByIsOK_shouldReturnAppointmentsWithStatus() {

        List<RDV> result =
                rdvRepository.findByIsOK(1);

        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(
                1,
                result.get(0).getIsOK()
        );

        assertEquals(
                "Consultation médicale",
                result.get(0).getMotif()
        );
    }

    // --------------------------------------------------
    // Recherche par prestataire
    // --------------------------------------------------

    @Test
    void findByPrestatairesId_shouldReturnAppointments() {

        List<RDV> result =
                rdvRepository.findByPrestatairesId(
                        prestataire.getId()
                );

        assertNotNull(result);
        assertEquals(1, result.size());

        RDV found = result.get(0);

        assertEquals(
                "Dupont",
                found.getPrestataires().getNom()
        );

        assertEquals(
                "Consultation médicale",
                found.getMotif()
        );
    }

    // --------------------------------------------------
    // Requête JPQL personnalisée
    // --------------------------------------------------

    @Test
    void findFullByUserId_shouldLoadAppointmentRelations() {

        List<RDV> result =
                rdvRepository.findFullByUserId(
                        user.getId()
                );

        assertNotNull(result);
        assertEquals(1, result.size());

        RDV found = result.get(0);

        // Rendez-vous
        assertEquals(
                "Consultation médicale",
                found.getMotif()
        );

        // Prestataire
        assertNotNull(found.getPrestataires());

        assertEquals(
                "Dupont",
                found.getPrestataires().getNom()
        );

        // Profession
        assertNotNull(found.getProfessions());

        assertEquals(
                "Médecin",
                found.getProfessions().getNom()
        );

        // Adresse
        assertNotNull(found.getAdresses());

        assertEquals(
                "Aix-en-Provence",
                found.getAdresses().getVille()
        );

        // Rappels
        assertNotNull(found.getRappels());

        assertEquals(
                1,
                found.getRappels().size()
        );

        assertEquals(
                "EMAIL",
                found.getRappels()
                        .get(0)
                        .getTypeAlerte()
        );
    }
}