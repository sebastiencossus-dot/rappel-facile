package com.msjpa.services;

import com.msjpa.models.*;
import com.msjpa.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RdvServiceTest {

    @Mock
    private RdvRepository rdvRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PrestataireRepository prestataireRepository;

    @Mock
    private ProfessionRepository professionRepository;

    @Mock
    private AdresseRepository adresseRepository;

    @Mock
    private RappelsRepository rappelsRepository;

    @InjectMocks
    private RdvService rdvService;

    private User user;
    private RDV rdv;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@test.fr");

        rdv = new RDV();
        rdv.setId(10);
        rdv.setDateRdv(LocalDateTime.of(2026, 9, 25, 14, 30));
        rdv.setMotif("Consultation");
        rdv.setIsOK(1);
        rdv.setUser(user);
        rdv.setRappels(new ArrayList<>());
    }

    @Test
    void getRdvByUser_shouldReturnUserAppointments() {

        when(rdvRepository.findByUser_Email("test@test.fr"))
                .thenReturn(List.of(rdv));

        List<RDV> result = rdvService.getRdvByUser("test@test.fr");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Consultation", result.get(0).getMotif());

        verify(rdvRepository).findByUser_Email("test@test.fr");
    }

    @Test
    void toDTO_shouldConvertRdvToDTO() {

        Prestataires prestataire = new Prestataires();
        prestataire.setId(2);
        prestataire.setNom("Dupont");
        prestataire.setPrenom("Jean");

        Professions profession = new Professions();
        profession.setId(3);
        profession.setNom("Médecin");

        Adresses adresse = new Adresses();
        adresse.setId(4);
        adresse.setNumero("10");
        adresse.setRue("Rue de la Paix");
        adresse.setVille("Aix-en-Provence");
        adresse.setCodepostal("13100");

        rdv.setPrestataires(prestataire);
        rdv.setProfessions(profession);
        rdv.setAdresses(adresse);

        RdvDTO result = rdvService.toDTO(rdv);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("Consultation", result.getMotif());
        assertEquals(1, result.getIsOK());

        assertEquals("Dupont", result.getPrestataireNom());
        assertEquals("Jean", result.getPrestatairePrenom());
        assertEquals("Médecin", result.getProfessionNom());

        assertEquals("10", result.getAdresseNumero());
        assertEquals("Rue de la Paix", result.getAdresseRue());
        assertEquals("13100", result.getAdresseCodePostal());
        assertEquals("Aix-en-Provence", result.getAdresseVille());

        assertEquals(1, result.getUserId());
    }

    @Test
    void createRdv_shouldCreateAppointmentAndDefaultReminder() {

        RdvPrestDTO dto = new RdvPrestDTO();
        dto.setDateRdv(LocalDateTime.of(2026, 10, 1, 9, 0));
        dto.setMotif("Dentiste");
        dto.setUserId(1);
        dto.setPrestataireId(2);
        dto.setProfessionId(3);
        dto.setAdresseId(4);

        Prestataires prestataire = new Prestataires();
        prestataire.setId(2);

        Professions profession = new Professions();
        profession.setId(3);

        Adresses adresse = new Adresses();
        adresse.setId(4);

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(prestataireRepository.findById(2))
                .thenReturn(Optional.of(prestataire));

        when(professionRepository.findById(3))
                .thenReturn(Optional.of(profession));

        when(adresseRepository.findById(4))
                .thenReturn(Optional.of(adresse));

        when(rdvRepository.save(any(RDV.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RDV result = rdvService.createRdv(dto);

        assertNotNull(result);
        assertEquals("Dentiste", result.getMotif());
        assertEquals(1, result.getIsOK());

        assertEquals(user, result.getUser());
        assertEquals(prestataire, result.getPrestataires());
        assertEquals(profession, result.getProfessions());
        assertEquals(adresse, result.getAdresses());

        verify(rdvRepository).save(any(RDV.class));

        verify(rappelsRepository).save(argThat(rappel ->
                "EMAIL".equals(rappel.getTypeAlerte())
                        && Integer.valueOf(1440).equals(rappel.getDelai())
                        && rappel.getRdv() == result
        ));
    }

    @Test
    void updateRdv_shouldUpdateAppointmentWhenUserIsOwner() {

        RdvUpdateDTO dto = new RdvUpdateDTO();
        dto.setDateRdv(LocalDateTime.of(2026, 10, 5, 16, 0));
        dto.setMotif("Consultation modifiée");

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        when(rdvRepository.save(rdv))
                .thenReturn(rdv);

        RDV result = rdvService.updateRdv(
                10,
                dto,
                "test@test.fr"
        );

        assertEquals(
                "Consultation modifiée",
                result.getMotif()
        );

        assertEquals(
                LocalDateTime.of(2026, 10, 5, 16, 0),
                result.getDateRdv()
        );

        verify(rdvRepository).save(rdv);
    }

    @Test
    void updateRdv_shouldRejectUpdateWhenUserIsNotOwner() {

        RdvUpdateDTO dto = new RdvUpdateDTO();
        dto.setMotif("Modification interdite");

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> rdvService.updateRdv(
                        10,
                        dto,
                        "autre@test.fr"
                )
        );

        assertEquals(
                "Accès non autorisé",
                exception.getMessage()
        );

        verify(rdvRepository, never()).save(any());
    }

    @Test
    void getRdvById_shouldReturnAppointmentWhenUserIsOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RDV result = rdvService.getRdvById(
                10,
                "test@test.fr"
        );

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("Consultation", result.getMotif());
    }

    @Test
    void getRdvById_shouldRejectAccessWhenUserIsNotOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> rdvService.getRdvById(
                        10,
                        "autre@test.fr"
                )
        );

        assertEquals(
                "Unauthorized",
                exception.getMessage()
        );
    }

    @Test
    void deleteRdv_shouldDeleteAppointmentAndRemindersWhenUserIsOwner() {

        Rappels rappel = new Rappels();
        rappel.setId(5);
        rappel.setRdv(rdv);

        rdv.setRappels(List.of(rappel));

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        rdvService.deleteRdv(
                10,
                "test@test.fr"
        );

        verify(rappelsRepository)
                .deleteAll(rdv.getRappels());

        verify(rdvRepository)
                .delete(rdv);
    }

    @Test
    void deleteRdv_shouldRejectDeleteWhenUserIsNotOwner() {

        when(rdvRepository.findById(10))
                .thenReturn(Optional.of(rdv));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> rdvService.deleteRdv(
                        10,
                        "autre@test.fr"
                )
        );

        assertEquals(
                "Unauthorized",
                exception.getMessage()
        );

        verify(rappelsRepository, never())
                .deleteAll(any());

        verify(rdvRepository, never())
                .delete(any(RDV.class));
    }
}