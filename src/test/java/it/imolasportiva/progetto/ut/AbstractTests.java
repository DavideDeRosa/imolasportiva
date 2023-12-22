package it.imolasportiva.progetto.ut;

import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import it.imolasportiva.progetto.mapper.UtenteMapper;
import it.imolasportiva.progetto.repository.CampoRepository;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import it.imolasportiva.progetto.repository.UtenteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

@SpringBootTest
public class AbstractTests {

    @Autowired
    protected UtenteMapper utenteMapper;

    @Autowired
    protected PrenotazioneMapper prenotazioneMapper;

    @Autowired
    protected UtenteRepository utenteRepository;

    @Autowired
    protected PrenotazioneRepository prenotazioneRepository;

    @Autowired
    protected CampoRepository campoRepository;

    @BeforeAll
    public static void init() {
        URL propertiesDir = AbstractTests.class.getClassLoader().getResource("conf/ut");
        System.setProperty("openjdk.app.properties.dir", propertiesDir.getPath());
    }
}