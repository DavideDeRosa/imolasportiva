package it.imolasportiva.progetto.ut;

import imolasportiva.model.Prenotazione;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MapperTests extends AbstractTests {
    @Test
    void testMapUtenteEntity() {
        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setId(Long.valueOf(1));
        utenteEntity.setNome("Davide");
        utenteEntity.setCognome("De Rosa");
        utenteEntity.setDataNascita(new Date());
        utenteEntity.setTelefono("123");

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
    void testMapPrenotazioneEntity() {
        PrenotazioneEntity prenotazioneEntity = new PrenotazioneEntity();
        prenotazioneEntity.setId(Long.valueOf(1));
        prenotazioneEntity.setDataPrenotazione(new Date());
        prenotazioneEntity.setDurataPrenotazioneOre(2);
        prenotazioneEntity.setNumeroPartecipanti(2);
        prenotazioneEntity.setQuota(2);
        prenotazioneEntity.setDurataDeadlineCancellazione(2);

        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setId(Long.valueOf(1));
        utenteEntity.setNome("Davide");
        utenteEntity.setCognome("De Rosa");
        utenteEntity.setDataNascita(new Date());
        utenteEntity.setTelefono("123");

        prenotazioneEntity.setIdUtentePrenotato(utenteEntity);

        CampoEntity campoEntity = new CampoEntity();
        campoEntity.setId(Long.valueOf(1));
        campoEntity.setCodice("prova");
        campoEntity.setTipologia("prova");

        prenotazioneEntity.setCampo(campoEntity);

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
        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setId(Long.valueOf(1));
        utenteEntity.setNome("Davide");
        utenteEntity.setCognome("De Rosa");
        utenteEntity.setDataNascita(new Date());
        utenteEntity.setTelefono("123");

        utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = new CampoEntity();
        campoEntity.setId(Long.valueOf(1));
        campoEntity.setCodice("prova");
        campoEntity.setTipologia("prova");

        campoRepository.save(campoEntity);

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(Long.valueOf(1));
        prenotazione.setDataPrenotazione("24-09-2002 15:00");
        prenotazione.setDurataPrenotazioneOre("2");
        prenotazione.setNumeroPartecipanti("2");
        prenotazione.setQuota("2");
        prenotazione.setDurataDeadlineCancellazioneOre("2");
        prenotazione.setIdCampo(campoEntity.getId());
        prenotazione.setIdUtentePrenotato(utenteEntity.getId());

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
    void testMapUtenteWrongDate() {
        Utente utente = creaUtenteModel("Davide", "De Rosa", Long.valueOf(1), "errore", "123");

        try {
            utenteMapper.utenteToUtenteDTO(utente);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Error converting String to Date");
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
}