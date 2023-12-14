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

        Long id;

        try{
            id = Long.valueOf(idPrenotazione);
        }catch(NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PrenotazioneDTO prenotazioneDTO = prenotazioneBL.getPrenotazioneDTOById(id);

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

            // controllo anno giusto (sia int che valore)!

            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnno(Integer.parseInt(anno))) {
                prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
            }

            return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
        }else{
            List<Prenotazione> prenotazioneList = new ArrayList<>();

            // controllo anno e mese giusto (sia int che valore)!

            for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioneAnnoMese(Integer.parseInt(anno), Integer.parseInt(mese))) {
                prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
            }

            return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
        }
    }

    public ResponseEntity<Prenotazione> postPrenotazione(Prenotazione prenotazione) {
        log.info("Invocazione postPrenotazione()");

        // prima di proseguire bisogna controllare il corretto inserimento dei vari valori di prenotazione

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        prenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }
}
