package it.imolasportiva.progetto.api;

import imolasportiva.api.UtentiApi;
import imolasportiva.model.UtenteEntity;
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
    public ResponseEntity<UtenteEntity> getUtenteById(String idUtente) {
        log.info("Invocazione getUtenteById()");
        return new ResponseEntity<UtenteEntity>(utenteRepository.getUtente(idUtente), HttpStatus.OK);
    }

    public ResponseEntity<UtenteEntity> postUtente(UtenteEntity utenteEntity){
        log.info("Invocazione postUtente()", utenteEntity);
        return new ResponseEntity<UtenteEntity>(utenteRepository.putUtente(utenteEntity), HttpStatus.OK);
    }
}
