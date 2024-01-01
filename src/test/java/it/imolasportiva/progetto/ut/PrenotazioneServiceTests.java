package it.imolasportiva.progetto.ut;

import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.service.PrenotazioneBL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PrenotazioneServiceTests extends AbstractTests {

    @BeforeEach
    void drop() {
        prenotazioneRepository.deleteAll();
        campoRepository.deleteAll();
        utenteRepository.deleteAll();
    }

    @Autowired
    private PrenotazioneBL prenotazioneBL;

    @Test
    void testPostPrenotazione() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO postPrenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        assertEquals(prenotazioneDTO.getDataPrenotazione(), postPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), postPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getId(), postPrenotazioneDTO.getCampo().getId());
    }

    @Test
    void testGetPrenotazioneById() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO postPrenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        PrenotazioneDTO returnedPrenotazioneDTO = prenotazioneBL.getPrenotazioneDTOById(postPrenotazioneDTO.getId());

        assertEquals(prenotazioneDTO.getDataPrenotazione(), returnedPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), returnedPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), returnedPrenotazioneDTO.getCampo().getTipologia());
    }

    @Test
    void testGetPrenotazioneByIdFail() {
        try {
            prenotazioneBL.getPrenotazioneDTOById(Long.valueOf(1));
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "PrenotazioneNotFound - Prenotazione non presente!");
        }
    }

    @Test
    void testPutPrenotazione() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO createdPrenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        createdPrenotazioneDTO.setNumeroPartecipanti(10);

        PrenotazioneDTO putPrenotazioneDTO = prenotazioneBL.putPrenotazione(createdPrenotazioneDTO.getId(), createdPrenotazioneDTO);

        assertEquals(prenotazioneDTO.getDataPrenotazione(), putPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), putPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), putPrenotazioneDTO.getCampo().getTipologia());
    }

    @Test
    void testPutPrenotazioneFail() {
        try {
            prenotazioneBL.putPrenotazione(Long.valueOf(1), new PrenotazioneDTO());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "PrenotazioneNotFound - Prenotazione non presente!");
        }
    }

    @Test
    void testDeletePrenotazione() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO createdPrenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        prenotazioneBL.getPrenotazioneDTOById(createdPrenotazioneDTO.getId());

        prenotazioneBL.deletePrenotazione(createdPrenotazioneDTO.getId());

        try {
            prenotazioneBL.getPrenotazioneDTOById(createdPrenotazioneDTO.getId());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "PrenotazioneNotFound - Prenotazione non presente!");
        }
    }

    @Test
    void testGetPrenotazioniAll() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO1 = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO2 = creaPrenotazioneDTO(LocalDateTime.now(), LocalDateTime.now(), 2, 2, 2, 2, utenteEntity, campoEntity);

        prenotazioneBL.postPrenotazione(prenotazioneDTO);
        prenotazioneBL.postPrenotazione(prenotazioneDTO1);
        prenotazioneBL.postPrenotazione(prenotazioneDTO2);

        List<PrenotazioneDTO> list = prenotazioneBL.getPrenotazioni(null, null);

        assertEquals(3, list.size());
    }

    @Test
    void testGetPrenotazioniAnno() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 00:00", formatter), LocalDateTime.parse("25-10-2200 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO1 = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2201 00:00", formatter), LocalDateTime.parse("25-10-2201 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO2 = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2202 00:00", formatter), LocalDateTime.parse("25-10-2202 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        prenotazioneBL.postPrenotazione(prenotazioneDTO);
        prenotazioneBL.postPrenotazione(prenotazioneDTO1);
        prenotazioneBL.postPrenotazione(prenotazioneDTO2);

        List<PrenotazioneDTO> list = prenotazioneBL.getPrenotazioni(2200, null);

        assertEquals(1, list.size());

        list = prenotazioneBL.getPrenotazioni(2201, null);

        assertEquals(1, list.size());

        list = prenotazioneBL.getPrenotazioni(2300, null);

        assertEquals(0, list.size());
    }

    @Test
    void testGetPrenotazioniAnnoMese() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 00:00", formatter), LocalDateTime.parse("25-10-2200 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO1 = creaPrenotazioneDTO(LocalDateTime.parse("25-11-2200 00:00", formatter), LocalDateTime.parse("25-11-2200 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneDTO prenotazioneDTO2 = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2202 00:00", formatter), LocalDateTime.parse("25-10-2202 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        prenotazioneBL.postPrenotazione(prenotazioneDTO);
        prenotazioneBL.postPrenotazione(prenotazioneDTO1);
        prenotazioneBL.postPrenotazione(prenotazioneDTO2);

        List<PrenotazioneDTO> list = prenotazioneBL.getPrenotazioni(2200, 10);

        assertEquals(1, list.size());

        list = prenotazioneBL.getPrenotazioni(2200, 11);

        assertEquals(1, list.size());

        list = prenotazioneBL.getPrenotazioni(2202, 8);

        assertEquals(0, list.size());
    }

    @Test
    void testGetPrenotazioniMeseFail() {
        try {
            prenotazioneBL.getPrenotazioni(null, 10);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "AnnoNotFound - Filtro errato! Non puoi richiedere un filtro per il mese senza un anno specifico!");
        }
    }

    @Test
    void testGetPrenotazioniAnnoMeseFail() {
        try {
            prenotazioneBL.getPrenotazioni(2200, 13);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "MeseError - Mese non compreso tra 1 e 12!");
        }
    }

    @Test
    void testValidPrenotazionePost() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePost(prenotazioneDTO);

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), validPrenotazioneDTO.getCampo().getTipologia());
    }

    @Test
    void testValidPrenotazionePostFailData() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 23:00", formatter), LocalDateTime.parse("26-10-2200 01:00", formatter), 3, 2, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "PrenotazioneWrongTime - La prenotazione è errata! Può essere eseguita una prenotazione tutti i giorni dalle 8 alle 24, tranne il Martedì!");
        }
    }

    @Test
    void testValidPrenotazionePostFailNumP() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 5, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "NumeroPartecipantiError - Il numero di partecipanti non è corretto! (2,4,10)");
        }
    }

    @Test
    void testValidPrenotazionePostFailTipoCampo1() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 10, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoError - Campo non corretto!");
        }
    }

    @Test
    void testValidPrenotazionePostFailTipoCampo2() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 2, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoError - Campo non corretto!");
        }
    }

    @Test
    void testValidPrenotazionePostCampoNullTennis() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 2, utenteEntity, null);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePost(prenotazioneDTO);

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(campoEntity.getId(), validPrenotazioneDTO.getCampo().getId());
    }

    @Test
    void testValidPrenotazionePostCampoNullCalcio() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, null);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePost(prenotazioneDTO);

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(campoEntity.getId(), validPrenotazioneDTO.getCampo().getId());
    }

    @Test
    void testValidPrenotazionePostCampoNullEmpty() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, null);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotAvailable - Campo non disponibile per la prenotazione in quell'ora!");
        }
    }

    @Test
    void testValidPrenotazionePostCampoNotAv() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotAvailable - Campo non disponibile per la prenotazione in quell'ora!");
        }
    }

    @Test
    void testValidPrenotazionePut() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 3, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePut(prenotazioneDTO, prenotazioneEntity.getId());

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getTipologia(), validPrenotazioneDTO.getCampo().getTipologia());
        assertEquals(prenotazioneDTO.getDurataDeadlineCancellazione(), validPrenotazioneDTO.getDurataDeadlineCancellazione());
    }

    @Test
    void testValidPrenotazionePutFailData() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 06:00", formatter), LocalDateTime.parse("26-10-2200 09:00", formatter), 3, 2, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePut(prenotazioneDTO, null);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "PrenotazioneWrongTime - La prenotazione è errata! Può essere eseguita una prenotazione tutti i giorni dalle 8 alle 24, tranne il Martedì!");
        }
    }

    @Test
    void testValidPrenotazionePutFailNumP() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 7, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePost(prenotazioneDTO);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "NumeroPartecipantiError - Il numero di partecipanti non è corretto! (2,4,10)");
        }
    }

    @Test
    void testValidPrenotazionePutFailTipoCampo1() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 10, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePut(prenotazioneDTO, null);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoError - Campo non corretto!");
        }
    }

    @Test
    void testValidPrenotazionePutFailTipoCampo2() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 20:00", formatter), LocalDateTime.parse("25-10-2200 23:00", formatter), 3, 2, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePut(prenotazioneDTO, null);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoError - Campo non corretto!");
        }
    }

    @Test
    void testValidPrenotazionePutCampoNullTennis() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 2, 2, 2, utenteEntity, null);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePut(prenotazioneDTO, prenotazioneEntity.getId());

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(campoEntity.getId(), validPrenotazioneDTO.getCampo().getId());
    }

    @Test
    void testValidPrenotazionePutCampoNullCalcio() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, null);

        PrenotazioneDTO validPrenotazioneDTO = prenotazioneBL.validPrenotazionePut(prenotazioneDTO, prenotazioneEntity.getId());

        assertEquals(prenotazioneDTO.getDataPrenotazione(), validPrenotazioneDTO.getDataPrenotazione());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), validPrenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(campoEntity.getId(), validPrenotazioneDTO.getCampo().getId());
    }

    @Test
    void testValidPrenotazionePutCampoNullEmpty() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 08:00", formatter), LocalDateTime.parse("25-10-2200 10:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 09:00", formatter), LocalDateTime.parse("25-10-2200 11:00", formatter), 2, 10, 2, 2, utenteEntity, null);

        try {
            prenotazioneBL.validPrenotazionePut(prenotazioneDTO, prenotazioneEntity.getId());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotAvailable - Campo non disponibile per la prenotazione in quell'ora!");
        }
    }

    @Test
    void testValidPrenotazionePutCampoNotAv() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 08:00", formatter), LocalDateTime.parse("25-10-2200 10:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2200 10:00", formatter), LocalDateTime.parse("25-10-2200 12:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneDTO prenotazioneDTO = creaPrenotazioneDTO(LocalDateTime.parse("25-10-2200 09:00", formatter), LocalDateTime.parse("25-10-2200 11:00", formatter), 2, 10, 2, 2, utenteEntity, campoEntity);

        try {
            prenotazioneBL.validPrenotazionePut(prenotazioneDTO, prenotazioneEntity.getId());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotAvailable - Campo non disponibile per la prenotazione in quell'ora!");
        }
    }
}
