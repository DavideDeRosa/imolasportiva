package it.imolasportiva.progetto.ut;

import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Transactional
public class RepositoryTests extends AbstractTests {
    /*
    @Test
    void testRepoFindByYear() {
        loadPrenotazioni();

        List<PrenotazioneEntity> list = prenotazioneRepository.findByYear(2200);

        assertEquals(1, list.size());

        list = prenotazioneRepository.findByYear(2201);

        assertEquals(1, list.size());

        list = prenotazioneRepository.findByYear(2210);

        assertEquals(0, list.size());
    }

    @Test
    void testRepoFindByYearAndMonth() {
        loadPrenotazioni();

        List<PrenotazioneEntity> list = prenotazioneRepository.findByYearAndMonth(2200, 9);

        assertEquals(1, list.size());

        List<PrenotazioneEntity> list1 = prenotazioneRepository.findByYearAndMonth(2201, 10);

        assertEquals(1, list1.size());

        List<PrenotazioneEntity> list2 = prenotazioneRepository.findByYearAndMonth(2200, 12);

        assertEquals(0, list2.size());
    }

    @Test
    void testRepoFindCampoOccupatoPost() {
        PrenotazioneEntity prenotazioneEntity = loadPrenotazioniOccupatiELiberi();

        List<PrenotazioneEntity> list = prenotazioneRepository.findCampoOccupatoPost(prenotazioneEntity.getCampo().getId(), prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione());

        assertEquals(1, list.size());
    }


    @Test
    void testRepoFindCampoOccupatoPut() {
        PrenotazioneEntity prenotazioneEntity = loadPrenotazioniOccupatiELiberi();

        List<PrenotazioneEntity> list = prenotazioneRepository.findCampoOccupatoPut(prenotazioneEntity.getCampo().getId(), prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione(), prenotazioneEntity.getId());

        assertEquals(1, list.size());
    }


    @Test
    void testRepoFindCampiLiberiPost() {
        PrenotazioneEntity prenotazioneEntity = loadPrenotazioniOccupatiELiberi();

        List<CampoEntity> list = campoRepository.findCampiLiberiPost(prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione(), prenotazioneEntity.getCampo().getTipologia());

        assertEquals(0, list.size());

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(2), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        list = campoRepository.findCampiLiberiPost(prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione(), prenotazioneEntity.getCampo().getTipologia());

        assertEquals(1, list.size());
    }

    @Test
    void testRepoFindCampiLiberiPut() {
        PrenotazioneEntity prenotazioneEntity = loadPrenotazioniOccupatiELiberi();

        List<CampoEntity> list = campoRepository.findCampiLiberiPut(prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione(), prenotazioneEntity.getCampo().getTipologia(), prenotazioneEntity.getId());

        assertEquals(1, list.size());

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(2), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        list = campoRepository.findCampiLiberiPut(prenotazioneEntity.getDataPrenotazione(), prenotazioneEntity.getDataFinePrenotazione(), prenotazioneEntity.getCampo().getTipologia(), prenotazioneEntity.getId());

        assertEquals(2, list.size());
    }

    PrenotazioneEntity loadPrenotazioniOccupatiELiberi() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        PrenotazioneEntity prenotazione = creaPrenotazioneEntity(Long.valueOf(1), LocalDateTime.parse("25-10-2100 00:00", formatter), LocalDateTime.parse("25-10-2100 02:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        prenotazione = prenotazioneRepository.save(prenotazione);

        return prenotazione;
    }

    void loadPrenotazioni() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoEntity = campoRepository.save(campoEntity);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        PrenotazioneEntity prenotazione2 = creaPrenotazioneEntity(Long.valueOf(2), LocalDateTime.parse("24-09-2200 01:00", formatter), LocalDateTime.parse("24-09-2100 03:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneEntity prenotazione3 = creaPrenotazioneEntity(Long.valueOf(3), LocalDateTime.parse("24-10-2201 01:00", formatter), LocalDateTime.parse("24-10-2100 03:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);
        PrenotazioneEntity prenotazione4 = creaPrenotazioneEntity(Long.valueOf(4), LocalDateTime.parse("24-09-2202 01:00", formatter), LocalDateTime.parse("24-09-2101 03:00", formatter), 2, 2, 2, 2, utenteEntity, campoEntity);

        prenotazioneRepository.save(prenotazione2);
        prenotazioneRepository.save(prenotazione3);
        prenotazioneRepository.save(prenotazione4);
    }
    */
}
