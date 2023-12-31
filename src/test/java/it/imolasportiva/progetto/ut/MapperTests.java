package it.imolasportiva.progetto.ut;

import imolasportiva.model.Prenotazione;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.error.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MapperTests extends AbstractTests {

    @BeforeEach
    void drop() {
        prenotazioneRepository.deleteAll();
        campoRepository.deleteAll();
        utenteRepository.deleteAll();
    }

    @Test
    void testMapUtenteEntity() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        UtenteDTO utenteDTO = utenteMapper.utenteEntityToUtenteDTO(utenteEntity);

        //Testing from entity to DTO
        assertEquals(utenteEntity.getNome(), utenteDTO.getNome());
        assertEquals(utenteEntity.getCognome(), utenteDTO.getCognome());

        UtenteEntity utenteEntity1 = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);

        //Testing from DTO to entity
        assertEquals(utenteDTO.getNome(), utenteEntity1.getNome());
        assertEquals(utenteDTO.getCognome(), utenteEntity1.getCognome());

        //Testing from entity to entity
        assertEquals(utenteEntity.getNome(), utenteEntity1.getNome());
        assertEquals(utenteEntity.getCognome(), utenteEntity1.getCognome());
    }

    @Test
    void testMapUtente() {
        Utente utente = creaUtenteModel("Davide", "De Rosa", 1L, "24-09-2002", "123");

        UtenteDTO utenteDTO = utenteMapper.utenteToUtenteDTO(utente);

        //Testing from model to DTO
        assertEquals(utente.getNome(), utenteDTO.getNome());
        assertEquals(utente.getCognome(), utenteDTO.getCognome());

        Utente utente1 = utenteMapper.utenteDTOToUtente(utenteDTO);

        //Testing from DTO to model
        assertEquals(utenteDTO.getNome(), utente1.getNome());
        assertEquals(utenteDTO.getCognome(), utente1.getCognome());

        //Testing from model to model
        assertEquals(utente.getNome(), utente1.getNome());
        assertEquals(utente.getCognome(), utente1.getCognome());
    }

    @Test
    void testMapUtenteModelNull() {
        Utente utente = null;

        UtenteDTO utenteDTO = utenteMapper.utenteToUtenteDTO(utente);

        assertNull(utenteDTO);

        utente = utenteMapper.utenteDTOToUtente(utenteDTO);

        assertNull(utente);
    }

    @Test
    void testMapUtenteEntityNull() {
        UtenteEntity utenteEntity = null;

        UtenteDTO utenteDTO = utenteMapper.utenteEntityToUtenteDTO(utenteEntity);

        assertNull(utenteDTO);

        utenteEntity = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);

        assertNull(utenteEntity);
    }

    @Test
    void testMapUtenteWrongDate() {
        Utente utente = creaUtenteModel("Davide", "De Rosa", 1L, "errore", "123");

        try {
            utenteMapper.utenteToUtenteDTO(utente);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Error converting String to Date");
        }
    }

    @Test
    void testMapPrenotazioneEntity() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "prova");

        PrenotazioneEntity prenotazioneEntity = creaPrenotazioneEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);

        //Testing from entity to DTO
        assertEquals(prenotazioneEntity.getDataPrenotazione(), prenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneEntity.getIdUtentePrenotato().getNome(), prenotazioneDTO.getIdUtentePrenotato().getNome());
        assertEquals(prenotazioneEntity.getCampo().getTipologia(), prenotazioneDTO.getCampo().getTipologia());

        PrenotazioneEntity prenotazioneEntity1 = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);

        //Testing from DTO to entity
        assertEquals(prenotazioneDTO.getDataPrenotazione(), prenotazioneEntity1.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getNome(), prenotazioneEntity1.getIdUtentePrenotato().getNome());
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), prenotazioneEntity1.getCampo().getTipologia());

        //Testing from entity to entity
        assertEquals(prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity1.getDataPrenotazione());
        assertEquals(prenotazioneEntity.getIdUtentePrenotato().getNome(), prenotazioneEntity1.getIdUtentePrenotato().getNome());
        assertEquals(prenotazioneEntity.getCampo().getTipologia(), prenotazioneEntity1.getCampo().getTipologia());
    }

    @Test
    void testMapPrenotazione() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "24-09-2002 15:00", "24-09-2002 17:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        //Testing from model to DTO
        assertEquals(LocalDateTime.parse(prenotazione.getDataPrenotazione(), formatter), prenotazioneDTO.getDataPrenotazione());
        assertEquals(campoEntity.getTipologia(), prenotazioneDTO.getCampo().getTipologia());
        assertEquals(utenteEntity.getNome(), prenotazioneDTO.getIdUtentePrenotato().getNome());

        Prenotazione prenotazione1 = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);

        //Testing from DTO to model
        assertEquals(prenotazioneDTO.getDataPrenotazione(), LocalDateTime.parse(prenotazione1.getDataPrenotazione()));
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), campoEntity.getTipologia());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getNome(), utenteEntity.getNome());
    }

    @Test
    void testMapPrenotazioneModelNull() {
        Prenotazione prenotazione = null;

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        assertNull(prenotazioneDTO);

        prenotazione = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);

        assertNull(prenotazione);
    }

    @Test
    void testMapPrenotazioneEntityNull() {
        PrenotazioneEntity prenotazioneEntity = null;

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);

        assertNull(prenotazioneDTO);

        prenotazioneEntity = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);

        assertNull(prenotazioneEntity);
    }

    @Test
    void testMapPrenotazioneWrongDate() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "errore", "24-09-2002 17:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        try {
            prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Error converting String to LocalDateTime");
        }
    }

    @Test
    void testMapPrenotazioneCampoNull() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "24-09-2002 15:00", "24-09-2002 17:00", "2", "2", "2", "2", utenteEntity.getId(), Long.valueOf(0));

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        assertNull(prenotazioneDTO.getCampo());
    }

    @Test
    void testMapPrenotazioneCampoNotFound() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "24-09-2002 15:00", "24-09-2002 17:00", "2", "2", "2", "2", utenteEntity.getId(), Long.valueOf(-1));

        try {
            prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotFound - Campo non presente!");
        }
    }
}