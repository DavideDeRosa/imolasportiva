package it.imolasportiva.progetto.ut;

import org.junit.jupiter.api.BeforeEach;

public class RepositoryTests extends AbstractTests {

    @BeforeEach
    public void drop() {
        utenteRepository.deleteAll();
        prenotazioneRepository.deleteAll();
        campoRepository.deleteAll();
    }

    public void savePrenotazione() {

    }

}
