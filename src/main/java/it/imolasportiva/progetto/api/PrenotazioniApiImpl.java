package it.imolasportiva.progetto.api;

import imolasportiva.api.PrenotazioniApi;
import imolasportiva.model.Prenotazione;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.error.PrenotazioneNotFoundException;
import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import it.imolasportiva.progetto.service.PrenotazioneBL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class PrenotazioniApiImpl implements PrenotazioniApi {

    private final PrenotazioneBL prenotazioneBL;
    private final PrenotazioneMapper prenotazioneMapper;

    @Autowired
    public PrenotazioniApiImpl(PrenotazioneBL prenotazioneBL, PrenotazioneMapper prenotazioneMapper) {
        this.prenotazioneBL = prenotazioneBL;
        this.prenotazioneMapper = prenotazioneMapper;
    }

    public ResponseEntity<Prenotazione> getPrenotazioneById(Long idPrenotazione) {
        log.info("Invocazione getPrenotazioneById()");

        try{
            PrenotazioneDTO prenotazioneDTO = prenotazioneBL.getPrenotazioneDTOById(idPrenotazione);

            return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
        }catch (PrenotazioneNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Prenotazione>> getPrenotazioni(Integer anno, Integer mese) {
        log.info("Invocazione getPrenotazioni()");

        List<Prenotazione> prenotazioneList = new ArrayList<>();

        if(anno == null){
            if(mese == null){
                for (PrenotazioneDTO x : prenotazioneBL.findAll()) {
                    prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
                }

                return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(mese == null){
            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnno(anno)) {
                prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
            }

            return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
        }else{
            try{
                if(mese <= 0 || mese > 12){
                    throw new RuntimeException();
                }
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnnoMese(anno, mese)) {
                prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
            }

            return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
        }
    }

    public ResponseEntity<Prenotazione> postPrenotazione(Prenotazione prenotazione) {
        log.info("Invocazione postPrenotazione()");

        PrenotazioneDTO prenotazioneDTO;

        try{
            prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(prenotazioneDTO.getIdUtentePrenotato() == null ||
                prenotazioneDTO.getDataPrenotazione().getDay() == 2 ||
                prenotazioneDTO.getDataPrenotazione().getHours() < 8 ||
                prenotazioneDTO.getDataPrenotazione().getHours() > 23){ // chiedere conferma logica 23/24 e deprecated metodi!
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        prenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePrenotazione(Long idPrenotazione) {
        log.info("Invocazione deletePrenotazione()");

        prenotazioneBL.deletePrenotazione(idPrenotazione);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Prenotazione> putPrenotazione(Long idPrenotazione, Prenotazione prenotazione) {
        log.info("Invocazione deletePrenotazione()");

        try{
            PrenotazioneDTO prenotazioneDTO = prenotazioneBL.putPrenotazione(idPrenotazione, prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione));

            return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
        }catch (PrenotazioneNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
