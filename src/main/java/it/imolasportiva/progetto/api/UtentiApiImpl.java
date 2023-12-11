package it.imolasportiva.progetto.api;

import imolasportiva.api.UtentiApi;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.service.UtenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class UtentiApiImpl implements UtentiApi {

    private final UtenteService utenteService;
    @Autowired
    public UtentiApiImpl(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    public ResponseEntity<Utente> getUtenteById(String idUtente) {
        log.info("Invocazione getUtenteById()");
        Optional<UtenteEntity> utente = utenteService.findById(Long.valueOf(idUtente));
        if(!utente.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        UtenteEntity entity = utente.get();
        Utente utenteMapped = new Utente();
        utenteMapped.setId(String.valueOf(entity.getId()));
        utenteMapped.setNome(entity.getNome());
        utenteMapped.setCognome(entity.getCognome());
        utenteMapped.setTelefono(String.valueOf(entity.getTelefono()));
        utenteMapped.setDataNascita(String.valueOf(entity.getDataNascita()));

        return new ResponseEntity<>(utenteMapped, HttpStatus.OK);
    }

    public ResponseEntity<Utente> postUtente(Utente utente){
        log.info("Invocazione postUtente()", utente);

        UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setNome(utente.getNome());
        utenteEntity.setCognome(utente.getCognome());
        utenteEntity.setDataNascita(Date.from(Instant.now()));
        utenteEntity.setTelefono(Long.valueOf(utente.getTelefono()));

        utenteService.saveUtente(utenteEntity);
        return new ResponseEntity<>(utente, HttpStatus.OK);
    }
}
