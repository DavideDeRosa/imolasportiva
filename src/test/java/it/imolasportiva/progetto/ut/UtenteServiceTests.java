package it.imolasportiva.progetto.ut;

import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.service.UtenteBL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtenteServiceTests extends AbstractTests {

    @BeforeEach
    void drop() {
        prenotazioneRepository.deleteAll();
        campoRepository.deleteAll();
        utenteRepository.deleteAll();
    }

    @Autowired
    private UtenteBL utenteBL;

    @Test
    void testPostUtente() {
        UtenteDTO utenteDTO = creaUtenteDTO("Davide", "De Rosa", new Date(), "123");

        UtenteDTO createdUtenteDTO = utenteBL.postUtente(utenteDTO);

        assertEquals(utenteDTO.getNome(), createdUtenteDTO.getNome());
        assertEquals(utenteDTO.getCognome(), createdUtenteDTO.getCognome());
        assertEquals(utenteDTO.getDataNascita(), createdUtenteDTO.getDataNascita());
    }

    @Test
    void testGetUtenteById() {
        UtenteDTO utenteDTO = creaUtenteDTO("Davide", "De Rosa", new Date(), "123");

        UtenteDTO createdUtenteDTO = utenteBL.postUtente(utenteDTO);

        UtenteDTO returnedUtenteDTO = utenteBL.getUtenteDTObyId(createdUtenteDTO.getId());

        assertEquals(utenteDTO.getNome(), returnedUtenteDTO.getNome());
        assertEquals(utenteDTO.getCognome(), returnedUtenteDTO.getCognome());
        assertEquals(utenteDTO.getDataNascita(), returnedUtenteDTO.getDataNascita());
    }

    @Test
    void testGetUtenteByIdFail() {
        try {
            utenteBL.getUtenteDTObyId(Long.valueOf(1));
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "UtenteNotFound - Utente non presente!");
        }
    }

    @Test
    void testPutUtente() {
        UtenteDTO utenteDTO = creaUtenteDTO("Davide", "De Rosa", new Date(), "123");

        UtenteDTO createdUtenteDTO = utenteBL.postUtente(utenteDTO);

        utenteDTO.setNome("Riccardo");

        UtenteDTO putUtenteDTO = utenteBL.putUtente(createdUtenteDTO.getId(), utenteDTO);

        assertEquals(utenteDTO.getNome(), putUtenteDTO.getNome());
        assertEquals(utenteDTO.getCognome(), putUtenteDTO.getCognome());
        assertEquals(utenteDTO.getDataNascita(), putUtenteDTO.getDataNascita());
    }

    @Test
    void testPutUtenteFail() {
        try {
            utenteBL.putUtente(Long.valueOf(1), new UtenteDTO());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "UtenteNotFound - Utente non presente!");
        }
    }

    @Test
    void testDeleteUtente() {
        UtenteDTO utenteDTO = creaUtenteDTO("Davide", "De Rosa", new Date(), "123");

        UtenteDTO createdUtenteDTO = utenteBL.postUtente(utenteDTO);

        utenteBL.getUtenteDTObyId(createdUtenteDTO.getId());

        utenteBL.deleteUtente(createdUtenteDTO.getId());

        try {
            utenteBL.getUtenteDTObyId(createdUtenteDTO.getId());
            fail();
        } catch (ErrorException e) {
            assertEquals(e.getMessage(), "UtenteNotFound - Utente non presente!");
        }
    }
}
