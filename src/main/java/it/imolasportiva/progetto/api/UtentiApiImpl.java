package it.imolasportiva.progetto.api;

import imolasportiva.api.UtentiApi;
import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.error.UtenteNotFoundException;
import it.imolasportiva.progetto.mapper.UtenteMapper;
import it.imolasportiva.progetto.service.UtenteBL;
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

    private final UtenteBL utenteBL;
    private final UtenteMapper utenteMapper;
    @Autowired
    public UtentiApiImpl(UtenteMapper utenteMapper, UtenteBL utenteBL) {
        this.utenteMapper = utenteMapper;
        this.utenteBL = utenteBL;
    }

    public ResponseEntity<Utente> getUtenteById(Long idUtente) {
        log.info("Invocazione getUtenteById()");

        try{
            UtenteDTO utenteDTO = utenteBL.getUtenteDTObyId(idUtente);

            return new ResponseEntity<>(utenteMapper.utenteDTOToUtente(utenteDTO), HttpStatus.OK);
        }catch (UtenteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Utente> postUtente(Utente utente){
        log.info("Invocazione postUtente()");

        try{
            UtenteDTO utenteDTO = utenteMapper.utenteToUtenteDTO(utente);

            utenteDTO = utenteBL.postUtente(utenteDTO);

            return new ResponseEntity<>(utenteMapper.utenteDTOToUtente(utenteDTO), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteUtente(Long idUtente) {
        log.info("Invocazione deleteUtente()");

        try{
            utenteBL.deleteUtente(idUtente);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UtenteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Utente> putUtente(Long idUtente, Utente utente) {
        log.info("Invocazione putUtente()");

        try{
            UtenteDTO utenteDTO = utenteBL.putUtente(idUtente, utenteMapper.utenteToUtenteDTO(utente));

            return new ResponseEntity<>(utenteMapper.utenteDTOToUtente(utenteDTO), HttpStatus.OK);
        }catch(UtenteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
