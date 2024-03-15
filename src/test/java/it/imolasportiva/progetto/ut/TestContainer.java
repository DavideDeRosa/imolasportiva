package it.imolasportiva.progetto.ut;

import imolasportiva.model.Prenotazione;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import it.imolasportiva.progetto.ImolaSportivaApplication;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.repository.CampoRepository;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import it.imolasportiva.progetto.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ImolaSportivaApplication.class, properties = {"openjdk.app.properties.dir=src/test/resources/conf/it/"})
public class TestContainer {

    @LocalServerPort
    private Integer port;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private CampoRepository campoRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.withInitScript("db/V1__init.sql");
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        prenotazioneRepository.deleteAll();
    }

    @Test
    void testPrenotazioneGet() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 2, 10, 2, 2, utenteEntity, campoEntity));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/prenotazioni/" + prenotazioneEntity.getId())
                .then()
                .statusCode(200);
    }

    @Test
    void testPrenotazionePost() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "24-09-2200 15:00", "24-09-2200 17:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        given()
                .contentType(ContentType.JSON)
                .body(prenotazione)
                .when()
                .post("/api/v1/prenotazioni")
                .then()
                .statusCode(200);
    }

    @Test
    void testPrenotazioneGetFail() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "Calcio");

        campoEntity = campoRepository.save(campoEntity);

        prenotazioneRepository.save(creaPrenotazioneEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 2, 10, 2, 2, utenteEntity, campoEntity));

        prenotazioneRepository.save(creaPrenotazioneEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 2, 10, 2, 2, utenteEntity, campoEntity));

        PrenotazioneEntity prenotazioneEntity = prenotazioneRepository.save(creaPrenotazioneEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 2, 10, 2, 2, utenteEntity, campoEntity));


        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/prenotazioni/" + (prenotazioneEntity.getId() + 1))
                .then()
                .statusCode(404);
    }

    @Test
    void testPrenotazionePostFail() {
        UtenteEntity utenteEntity = creaUtenteEntity(1L, "Davide", "De Rosa", new Date(), "123");

        utenteEntity = utenteRepository.save(utenteEntity);

        CampoEntity campoEntity = creaCampoEntity(1L, "prova", "Tennis");

        campoEntity = campoRepository.save(campoEntity);

        Prenotazione prenotazione = creaPrenotazioneModel(1L, "24-09-2002 15:00", "24-09-2002 17:00", "2", "2", "2", "2", utenteEntity.getId(), campoEntity.getId());

        given()
                .contentType(ContentType.JSON)
                .body(prenotazione)
                .when()
                .post("/api/v1/prenotazioni")
                .then()
                .statusCode(400);
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
