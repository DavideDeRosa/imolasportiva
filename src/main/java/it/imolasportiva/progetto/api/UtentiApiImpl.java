package it.imolasportiva.progetto.api;

import imolasportiva.api.UtentiApi;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class UtentiApiImpl implements UtentiApi {

    @Autowired
    private UtenteRepository utenteRepository;
    public ResponseEntity<Utente> getUtenteById(String idUtente) {
        log.info("Invocazione getUtenteById()");
        return new ResponseEntity<Utente>(utenteRepository.getUtente(idUtente), HttpStatus.OK);
    }

    public ResponseEntity<Utente> postUtente(Utente utente){
        log.info("Invocazione postUtente()", utente);
        return new ResponseEntity<Utente>(utenteRepository.putUtente(utente), HttpStatus.OK);
    }
}
