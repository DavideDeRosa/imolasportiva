package it.imolasportiva.progetto.api;

import imolasportiva.api.UtentiApi;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.mapper.UtenteMapper;
import it.imolasportiva.progetto.service.UtenteBL;
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

    private final UtenteBL utenteBL;
    private final UtenteMapper utenteMapper;
    @Autowired
    public UtentiApiImpl(UtenteMapper utenteMapper, UtenteBL utenteBL) {
        this.utenteMapper = utenteMapper;
        this.utenteBL = utenteBL;
    }

    public ResponseEntity<Utente> getUtenteById(String idUtente) {
        log.info("Invocazione getUtenteById()");

        Long id;

        try{
            id = Long.valueOf(idUtente);
        }catch(NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UtenteDTO utenteDTO = utenteBL.getUtenteDTObyId(id);

        if(utenteDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(utenteMapper.utenteDTOToUtente(utenteDTO), HttpStatus.OK);
    }

    public ResponseEntity<Utente> postUtente(Utente utente){
        log.info("Invocazione postUtente()", utente);

        // prima di proseguire bisogna controllare il corretto inserimento dei vari valori di utente

        UtenteDTO utenteDTO = utenteMapper.utenteToUtenteDTO(utente);

        utenteDTO = utenteBL.postUtente(utenteDTO);

        return new ResponseEntity<>(utenteMapper.utenteDTOToUtente(utenteDTO), HttpStatus.OK);
    }
}
