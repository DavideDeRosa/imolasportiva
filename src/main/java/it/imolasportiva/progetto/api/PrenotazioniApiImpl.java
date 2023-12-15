package it.imolasportiva.progetto.api;

import imolasportiva.api.PrenotazioniApi;
import imolasportiva.model.Prenotazione;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
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

    public ResponseEntity<Prenotazione> getPrenotazioneById(String idPrenotazione) {
        log.info("Invocazione getPrenotazioneById()");

        try{
            Long.valueOf(idPrenotazione);
        }catch(NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PrenotazioneDTO prenotazioneDTO = prenotazioneBL.getPrenotazioneDTOById(Long.valueOf(idPrenotazione));

        if(prenotazioneDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }

    public ResponseEntity<List<Prenotazione>> getPrenotazioni(String anno, String mese) {
        log.info("Invocazione getPrenotazioni()");

        if(anno == null){
            if(mese == null){
                List<Prenotazione> prenotazioneList = new ArrayList<>();

                for (PrenotazioneDTO x : prenotazioneBL.findAll()) {
                    prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
                }

                return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(mese == null){
            List<Prenotazione> prenotazioneList = new ArrayList<>();

            try{
                Integer.parseInt(anno);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnno(Integer.parseInt(anno))) {
                prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
            }

            return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
        }else{
            List<Prenotazione> prenotazioneList = new ArrayList<>();

            try{
                Integer.parseInt(anno);
                int meseN = Integer.parseInt(mese);

                if(meseN <= 0 || meseN > 12){
                    throw new RuntimeException();
                }
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnnoMese(Integer.parseInt(anno), Integer.parseInt(mese))) {
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
}
