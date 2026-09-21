package com.msjpa.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msjpa.models.RDV;
import com.msjpa.models.RdvDTO;
import com.msjpa.models.RdvPrestDTO;
import com.msjpa.models.User;
import com.msjpa.repositories.RappelsRepository;
import com.msjpa.repositories.RdvRepository;
import com.msjpa.services.RdvService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RdvControllerMockMvcTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

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

        mockMvc = MockMvcBuilders
                .standaloneSetup(rdvController)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

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

    // --------------------------------------------------
    // GET /rdvs
    // --------------------------------------------------

    @Test
    void getRdvByUser_shouldReturn200AndAppointments()
            throws Exception {

        when(rdvService.getRdvByUser("test@test.fr"))
                .thenReturn(List.of(rdv));

        when(rdvService.toDTO(rdv))
                .thenReturn(rdvDTO);

        mockMvc.perform(
                        get("/rdvs")
                                .param(
                                        "email",
                                        "test@test.fr"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(
                                        MediaType.APPLICATION_JSON
                                )
                )
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(
                        jsonPath("$[0].motif")
                                .value("Consultation")
                )
                .andExpect(
                        jsonPath("$[0].isOK")
                                .value(1)
                );

        verify(rdvService)
                .getRdvByUser("test@test.fr");
    }

    // --------------------------------------------------
    // POST /rdvs
    // --------------------------------------------------

    @Test
    void createRdv_shouldReturn200AndCreatedAppointment()
            throws Exception {

        RdvPrestDTO dto = new RdvPrestDTO();

        dto.setDateRdv(
                LocalDateTime.of(
                        2026, 10, 1, 9, 0
                )
        );

        dto.setMotif("Dentiste");
        dto.setUserId(1);
        dto.setPrestataireId(2);
        dto.setProfessionId(3);
        dto.setAdresseId(4);

        RDV createdRdv = new RDV();

        createdRdv.setId(20);
        createdRdv.setDateRdv(dto.getDateRdv());
        createdRdv.setMotif("Dentiste");
        createdRdv.setIsOK(1);

        when(rdvService.createRdv(any(RdvPrestDTO.class)))
                .thenReturn(createdRdv);

        mockMvc.perform(
                        post("/rdvs")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(dto)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(
                                        MediaType.APPLICATION_JSON
                                )
                )
                .andExpect(
                        jsonPath("$.id")
                                .value(20)
                )
                .andExpect(
                        jsonPath("$.motif")
                                .value("Dentiste")
                )
                .andExpect(
                        jsonPath("$.isOK")
                                .value(1)
                );

        verify(rdvService)
                .createRdv(any(RdvPrestDTO.class));
    }

    // --------------------------------------------------
    // PATCH /rdvs/{id}/valider
    // Utilisateur propriétaire
    // --------------------------------------------------

    @Test
    void validerRdv_shouldReturn200WhenUserIsOwner()
            throws Exception {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        when(rdvRepository.save(any(RDV.class)))
                .thenAnswer(
                        invocation ->
                                invocation.getArgument(0)
                );

        mockMvc.perform(
                        patch("/rdvs/10/valider")
                                .param("statut", "0")
                                .param(
                                        "email",
                                        "test@test.fr"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(10)
                )
                .andExpect(
                        jsonPath("$.isOK")
                                .value(0)
                )
                .andExpect(
                        jsonPath("$.motif")
                                .value("Consultation")
                );

        verify(rdvRepository)
                .save(rdv);
    }

    // --------------------------------------------------
    // PATCH /rdvs/{id}/valider
    // Mauvais utilisateur
    // --------------------------------------------------

    @Test
    void validerRdv_shouldRejectWhenUserIsNotOwner()
            throws Exception {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        mockMvc.perform(
                        patch("/rdvs/10/valider")
                                .param("statut", "0")
                                .param(
                                        "email",
                                        "autre@test.fr"
                                )
                )
                .andExpect(
                        status().isForbidden()
                );

        verify(
                rdvRepository,
                never()
        ).save(any(RDV.class));
    }
}