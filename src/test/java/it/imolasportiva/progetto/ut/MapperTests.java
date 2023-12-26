package it.imolasportiva.progetto.ut;

import imolasportiva.model.Prenotazione;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.error.ErrorException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTests extends AbstractTests {
    @Test
    void testMapUtenteEntity() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        UtenteDTO utenteDTO = utenteMapper.utenteEntityToUtenteDTO(utenteEntity);

        //Testing from entity to DTO
        assertEquals(utenteEntity.getId(), utenteDTO.getId());
        assertEquals(utenteEntity.getNome(), utenteDTO.getNome());
        assertEquals(utenteEntity.getCognome(), utenteDTO.getCognome());

        UtenteEntity utenteEntity1 = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);

        //Testing from DTO to entity
        assertEquals(utenteDTO.getId(), utenteEntity1.getId());
        assertEquals(utenteDTO.getNome(), utenteEntity1.getNome());
        assertEquals(utenteDTO.getCognome(), utenteEntity1.getCognome());

        //Testing from entity to entity
        assertEquals(utenteEntity.getId(), utenteEntity1.getId());
        assertEquals(utenteEntity.getNome(), utenteEntity1.getNome());
        assertEquals(utenteEntity.getCognome(), utenteEntity1.getCognome());
    }

    @Test
    void testMapUtente() {
        Utente utente = creaUtenteModel("Davide", "De Rosa", Long.valueOf(1), "24-09-2002", "123");

        UtenteDTO utenteDTO = utenteMapper.utenteToUtenteDTO(utente);

        //Testing from model to DTO
        assertEquals(utente.getId(), utenteDTO.getId());
        assertEquals(utente.getNome(), utenteDTO.getNome());
        assertEquals(utente.getCognome(), utenteDTO.getCognome());

        Utente utente1 = utenteMapper.utenteDTOToUtente(utenteDTO);

        //Testing from DTO to model
        assertEquals(utenteDTO.getId(), utente1.getId());
        assertEquals(utenteDTO.getNome(), utente1.getNome());
        assertEquals(utenteDTO.getCognome(), utente1.getCognome());

        //Testing from model to model
        assertEquals(utente.getId(), utente1.getId());
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
        Utente utente = creaUtenteModel("Davide", "De Rosa", Long.valueOf(1), "errore", "123");

        try {
            utenteMapper.utenteToUtenteDTO(utente);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Error converting String to Date");
        }
    }

    @Test
    void testMapPrenotazioneEntity() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        PrenotazioneEntity prenotazioneEntity = creaPrenotazioneEntity(Long.valueOf(1), new Date(), 2, 2, 2, 2, utenteEntity, campoEntity);

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);

        //Testing from entity to DTO
        assertEquals(prenotazioneEntity.getId(), prenotazioneDTO.getId());
        assertEquals(prenotazioneEntity.getIdUtentePrenotato().getId(), prenotazioneDTO.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneEntity.getCampo().getId(), prenotazioneDTO.getCampo().getId());

        PrenotazioneEntity prenotazioneEntity1 = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);

        //Testing from DTO to entity
        assertEquals(prenotazioneDTO.getId(), prenotazioneEntity1.getId());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), prenotazioneEntity1.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneDTO.getCampo().getId(), prenotazioneEntity1.getCampo().getId());

        //Testing from entity to entity
        assertEquals(prenotazioneEntity.getId(), prenotazioneEntity1.getId());
        assertEquals(prenotazioneEntity.getIdUtentePrenotato().getId(), prenotazioneEntity1.getIdUtentePrenotato().getId());
        assertEquals(prenotazioneEntity.getCampo().getId(), prenotazioneEntity1.getCampo().getId());
    }

    @Test
    void testMapPrenotazione() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(Long.valueOf(1), "24-09-2002 15:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        //Testing from model to DTO
        assertEquals(prenotazione.getId(), prenotazioneDTO.getId());
        assertEquals(prenotazione.getIdCampo(), prenotazioneDTO.getCampo().getId());
        assertEquals(prenotazione.getIdUtentePrenotato(), prenotazioneDTO.getIdUtentePrenotato().getId());

        Prenotazione prenotazione1 = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);

        //Testing from DTO to model
        assertEquals(prenotazioneDTO.getId(), prenotazione1.getId());
        assertEquals(prenotazioneDTO.getCampo().getId(), prenotazione1.getIdCampo());
        assertEquals(prenotazioneDTO.getIdUtentePrenotato().getId(), prenotazione1.getIdUtentePrenotato());

        //Testing from model to model
        assertEquals(prenotazione.getId(), prenotazione1.getId());
        assertEquals(prenotazione.getIdCampo(), prenotazione1.getIdCampo());
        assertEquals(prenotazione.getIdUtentePrenotato(), prenotazione1.getIdUtentePrenotato());
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
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(1), "prova", "prova");

        campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(Long.valueOf(1), "errore", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        try {
            prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Error converting String to Date");
        }
    }

    @Test
    void testMapPrenotazioneCampoNull() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(0), "prova", "prova");

        campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(Long.valueOf(1), "24-09-2002 15:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        assertNull(prenotazioneDTO.getCampo());
    }

    @Test
    void testMapPrenotazioneCampoNotFound() {
        UtenteEntity utenteEntity = creaUtenteEntity(Long.valueOf(1), "Davide", "De Rosa", new Date(), "123");

        utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(Long.valueOf(-1), "prova", "prova");

        campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(Long.valueOf(1), "24-09-2002 15:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        try {
            prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "CampoNotFound - Campo non presente!");
        }
    }

    private Utente creaUtenteModel(String nome, String cognome, Long id, String data, String telefono) {
        Utente utente = new Utente();
        utente.setId(id);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setDataNascita(data);
        utente.setTelefono(telefono);
        return utente;
    }

    private UtenteEntity creaUtenteEntity(Long id, String nome, String cognome, Date data, String telefono) {
        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setId(id);
        utenteEntity.setNome(nome);
        utenteEntity.setCognome(cognome);
        utenteEntity.setDataNascita(data);
        utenteEntity.setTelefono(telefono);
        return utenteEntity;
    }

    private CampoEntity creaCampoEntity(Long id, String codice, String tipologia) {
        CampoEntity campoEntity = new CampoEntity();
        campoEntity.setId(id);
        campoEntity.setCodice(codice);
        campoEntity.setTipologia(tipologia);
        return campoEntity;
    }

    private PrenotazioneEntity creaPrenotazioneEntity(Long id, Date data, int durataPrenotazione, int numeroPartecipanti, int quota, int durataDeadline, UtenteEntity utenteEntity, CampoEntity campoEntity) {
        PrenotazioneEntity prenotazioneEntity = new PrenotazioneEntity();
        prenotazioneEntity.setId(id);
        prenotazioneEntity.setDataPrenotazione(data);
        prenotazioneEntity.setDurataPrenotazioneOre(durataPrenotazione);
        prenotazioneEntity.setNumeroPartecipanti(numeroPartecipanti);
        prenotazioneEntity.setQuota(quota);
        prenotazioneEntity.setDurataDeadlineCancellazione(durataDeadline);
        prenotazioneEntity.setIdUtentePrenotato(utenteEntity);
        prenotazioneEntity.setCampo(campoEntity);
        return prenotazioneEntity;
    }

    private Prenotazione creaPrenotazioneModel(Long id, String data, String durataPrenotazione, String numeroPartecipanti, String quota, String durataDeadline, Long utenteEntity, Long campoEntity) {
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(id);
        prenotazione.setDataPrenotazione(data);
        prenotazione.setDurataPrenotazioneOre(durataPrenotazione);
        prenotazione.setNumeroPartecipanti(numeroPartecipanti);
        prenotazione.setQuota(quota);
        prenotazione.setDurataDeadlineCancellazioneOre(durataDeadline);
        prenotazione.setIdCampo(campoEntity);
        prenotazione.setIdUtentePrenotato(utenteEntity);
        return prenotazione;
    }
}