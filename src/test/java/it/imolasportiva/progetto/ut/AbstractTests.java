package it.imolasportiva.progetto.ut;

import imolasportiva.model.Prenotazione;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import it.imolasportiva.progetto.mapper.UtenteMapper;
import it.imolasportiva.progetto.repository.CampoRepository;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import it.imolasportiva.progetto.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
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

    public void drop() {
        prenotazioneRepository.deleteAll();
        utenteRepository.deleteAll();
        campoRepository.deleteAll();
    }

    public Utente creaUtenteModel(String nome, String cognome, Long id, String data, String telefono) {
        Utente utente = new Utente();
        utente.setId(id);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setDataNascita(data);
        utente.setTelefono(telefono);
        return utente;
    }

    public UtenteEntity creaUtenteEntity(Long id, String nome, String cognome, Date data, String telefono) {
        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setId(id);
        utenteEntity.setNome(nome);
        utenteEntity.setCognome(cognome);
        utenteEntity.setDataNascita(data);
        utenteEntity.setTelefono(telefono);
        return utenteEntity;
    }

    public CampoEntity creaCampoEntity(Long id, String codice, String tipologia) {
        CampoEntity campoEntity = new CampoEntity();
        campoEntity.setId(id);
        campoEntity.setCodice(codice);
        campoEntity.setTipologia(tipologia);
        return campoEntity;
    }

    public PrenotazioneEntity creaPrenotazioneEntity(Long id, LocalDateTime data, LocalDateTime dataFine, int durataPrenotazione, int numeroPartecipanti, int quota, int durataDeadline, UtenteEntity utenteEntity, CampoEntity campoEntity) {
        PrenotazioneEntity prenotazioneEntity = new PrenotazioneEntity();
        prenotazioneEntity.setId(id);
        prenotazioneEntity.setDataPrenotazione(data);
        prenotazioneEntity.setDataFinePrenotazione(dataFine);
        prenotazioneEntity.setDurataPrenotazioneOre(durataPrenotazione);
        prenotazioneEntity.setNumeroPartecipanti(numeroPartecipanti);
        prenotazioneEntity.setQuota(quota);
        prenotazioneEntity.setDurataDeadlineCancellazione(durataDeadline);
        prenotazioneEntity.setIdUtentePrenotato(utenteEntity);
        prenotazioneEntity.setCampo(campoEntity);
        return prenotazioneEntity;
    }

    public Prenotazione creaPrenotazioneModel(Long id, String data, String dataFine, String durataPrenotazione, String numeroPartecipanti, String quota, String durataDeadline, Long utenteEntity, Long campoEntity) {
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(id);
        prenotazione.setDataPrenotazione(data);
        prenotazione.setDataFinePrenotazione(dataFine);
        prenotazione.setDurataPrenotazioneOre(durataPrenotazione);
        prenotazione.setNumeroPartecipanti(numeroPartecipanti);
        prenotazione.setQuota(quota);
        prenotazione.setDurataDeadlineCancellazioneOre(durataDeadline);
        prenotazione.setIdCampo(campoEntity);
        prenotazione.setIdUtentePrenotato(utenteEntity);
        return prenotazione;
    }
}