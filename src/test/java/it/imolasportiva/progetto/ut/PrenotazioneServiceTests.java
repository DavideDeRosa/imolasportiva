package it.imolasportiva.progetto.ut;

import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.service.PrenotazioneBL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@Transactional
public class PrenotazioneServiceTests extends AbstractTests {

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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
}
