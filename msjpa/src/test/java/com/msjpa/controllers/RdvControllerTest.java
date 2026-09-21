package com.msjpa.controllers;

import com.msjpa.models.*;
import com.msjpa.repositories.RappelsRepository;
import com.msjpa.repositories.RdvRepository;
import com.msjpa.services.RdvService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RdvControllerTest {

    @Mock
    private RdvService rdvService;

    @Mock
    private RdvRepository rdvRepository;

    @Mock
    private RappelsRepository rappelsRepository;

    @InjectMocks
    private RdvController rdvController;

    private User user;
    private RDV rdv;
    private RdvDTO rdvDTO;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setEmail("test@test.fr");

        rdv = new RDV();
        rdv.setId(10);
        rdv.setDateRdv(
                LocalDateTime.of(2026, 9, 25, 14, 30)
        );
        rdv.setMotif("Consultation");
        rdv.setIsOK(1);
        rdv.setUser(user);

        rdvDTO = new RdvDTO();
        rdvDTO.setId(10);
        rdvDTO.setDateRdv(rdv.getDateRdv());
        rdvDTO.setMotif("Consultation");
        rdvDTO.setIsOK(1);
        rdvDTO.setUserId(1);
    }

    // -------------------------------------------------------
    // Récupération des rendez-vous d'un utilisateur
    // -------------------------------------------------------

    @Test
    void getRdvByUser_shouldReturnUserAppointments() {

        when(rdvService.getRdvByUser("test@test.fr"))
                .thenReturn(List.of(rdv));

        when(rdvService.toDTO(rdv))
                .thenReturn(rdvDTO);

        List<RdvDTO> result =
                rdvController.getRdvByUser("test@test.fr");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getId());
        assertEquals(
                "Consultation",
                result.get(0).getMotif()
        );

        verify(rdvService)
                .getRdvByUser("test@test.fr");

        verify(rdvService)
                .toDTO(rdv);
    }

    // -------------------------------------------------------
    // Consultation d'un rendez-vous
    // -------------------------------------------------------

    @Test
    void getRdv_shouldReturnAppointment() {

        when(rdvService.getRdvById(
                10,
                "test@test.fr"
        )).thenReturn(rdv);

        when(rdvService.toDTO(rdv))
                .thenReturn(rdvDTO);

        RdvDTO result =
                rdvController.getRdv(
                        10,
                        "test@test.fr"
                );

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(
                "Consultation",
                result.getMotif()
        );

        verify(rdvService)
                .getRdvById(
                        10,
                        "test@test.fr"
                );
    }

    // -------------------------------------------------------
    // Création d'un rendez-vous
    // -------------------------------------------------------

    @Test
    void createRdv_shouldCreateAppointment() {

        RdvPrestDTO dto = new RdvPrestDTO();
        dto.setUserId(1);
        dto.setMotif("Dentiste");
        dto.setDateRdv(
                LocalDateTime.of(
                        2026, 10, 1, 9, 0
                )
        );

        RDV createdRdv = new RDV();
        createdRdv.setId(20);
        createdRdv.setMotif("Dentiste");
        createdRdv.setIsOK(1);

        when(rdvService.createRdv(dto))
                .thenReturn(createdRdv);

        RDV result =
                rdvController.createRdv(dto);

        assertNotNull(result);
        assertEquals(20, result.getId());
        assertEquals(
                "Dentiste",
                result.getMotif()
        );
        assertEquals(1, result.getIsOK());

        verify(rdvService)
                .createRdv(dto);
    }

    // -------------------------------------------------------
    // Modification
    // -------------------------------------------------------

    @Test
    void updateRdv_shouldCallService() {

        RdvUpdateDTO dto =
                new RdvUpdateDTO();

        dto.setMotif(
                "Consultation modifiée"
        );

        RDV updatedRdv = new RDV();
        updatedRdv.setId(10);
        updatedRdv.setMotif(
                "Consultation modifiée"
        );

        when(rdvService.updateRdv(
                10,
                dto,
                "test@test.fr"
        )).thenReturn(updatedRdv);

        RDV result =
                rdvController.updateRdv(
                        10,
                        dto,
                        "test@test.fr"
                );

        assertNotNull(result);

        assertEquals(
                "Consultation modifiée",
                result.getMotif()
        );

        verify(rdvService)
                .updateRdv(
                        10,
                        dto,
                        "test@test.fr"
                );
    }

    // -------------------------------------------------------
    // Suppression
    // -------------------------------------------------------

    @Test
    void deleteRdv_shouldCallService() {

        doNothing()
                .when(rdvService)
                .deleteRdv(
                        10,
                        "test@test.fr"
                );

        rdvController.deleteRdv(
                10,
                "test@test.fr"
        );

        verify(rdvService)
                .deleteRdv(
                        10,
                        "test@test.fr"
                );
    }

    // -------------------------------------------------------
    // Validation du rendez-vous
    // -------------------------------------------------------

    @Test
    void validerRdv_shouldValidateAppointmentWhenUserIsOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        when(rdvRepository.save(rdv))
                .thenReturn(rdv);

        RDV result =
                rdvController.validerRdv(
                        10,
                        0,
                        "test@test.fr"
                );

        assertNotNull(result);

        assertEquals(
                0,
                result.getIsOK()
        );

        verify(rdvRepository)
                .save(rdv);
    }

    // -------------------------------------------------------
    // Sécurité validation
    // -------------------------------------------------------

    @Test
    void validerRdv_shouldRejectWhenUserIsNotOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> rdvController.validerRdv(
                                10,
                                0,
                                "autre@test.fr"
                        )
                );

        assertEquals(
                "Accès non autorisé",
                exception.getMessage()
        );

        verify(
                rdvRepository,
                never()
        ).save(any(RDV.class));
    }

    // -------------------------------------------------------
    // Ajout d'une alerte
    // -------------------------------------------------------

    @Test
    void addAlerte_shouldCreateReminderWhenUserIsOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        when(rappelsRepository.save(
                any(Rappels.class)
        )).thenAnswer(
                invocation ->
                        invocation.getArgument(0)
        );

        Rappels result =
                rdvController.addAlerte(
                        10,
                        "EMAIL",
                        60,
                        "test@test.fr"
                );

        assertNotNull(result);

        assertEquals(
                "EMAIL",
                result.getTypeAlerte()
        );

        assertEquals(
                60,
                result.getDelai()
        );

        assertEquals(
                rdv,
                result.getRdv()
        );

        verify(rappelsRepository)
                .save(any(Rappels.class));
    }

    // -------------------------------------------------------
    // Sécurité ajout d'une alerte
    // -------------------------------------------------------

    @Test
    void addAlerte_shouldRejectWhenUserIsNotOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> rdvController.addAlerte(
                                10,
                                "EMAIL",
                                60,
                                "autre@test.fr"
                        )
                );

        assertEquals(
                "Accès non autorisé",
                exception.getMessage()
        );

        verify(
                rappelsRepository,
                never()
        ).save(any(Rappels.class));
    }

    // -------------------------------------------------------
    // Liste administrateur
    // -------------------------------------------------------

    @Test
    void getAllRdv_shouldReturnAllAppointments() {

        when(rdvRepository.findAll())
                .thenReturn(List.of(rdv));

        when(rdvService.toDTO(rdv))
                .thenReturn(rdvDTO);

        List<RdvDTO> result =
                rdvController.getAllRdv();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(rdvRepository)
                .findAll();
    }

    // -------------------------------------------------------
    // Filtrage administrateur par statut
    // -------------------------------------------------------

    @Test
    void getRdvByStatut_shouldReturnAppointmentsWithStatus() {

        when(rdvRepository.findByIsOK(1))
                .thenReturn(List.of(rdv));

        when(rdvService.toDTO(rdv))
                .thenReturn(rdvDTO);

        List<RdvDTO> result =
                rdvController.getRdvByStatut(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getIsOK());

        verify(rdvRepository)
                .findByIsOK(1);
    }

    // -------------------------------------------------------
    // Suppression administrateur
    // -------------------------------------------------------

    @Test
    void deleteRdvAdmin_shouldDeleteAppointment() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        rdvController.deleteRdvAdmin(10);

        verify(rdvRepository)
                .delete(rdv);
    }
}