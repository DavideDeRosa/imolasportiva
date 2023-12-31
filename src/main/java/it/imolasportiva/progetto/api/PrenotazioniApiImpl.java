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

    public ResponseEntity<Prenotazione> getPrenotazioneById(Long idPrenotazione) {
        log.info("Invocazione getPrenotazioneById()");

        PrenotazioneDTO prenotazioneDTO = prenotazioneBL.getPrenotazioneDTOById(idPrenotazione);

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }

    public ResponseEntity<List<Prenotazione>> getPrenotazioni(Integer anno, Integer mese) {
        log.info("Invocazione getPrenotazioni()");

        List<Prenotazione> prenotazioneList = new ArrayList<>();

        for (PrenotazioneDTO x : prenotazioneBL.getPrenotazioni(anno, mese)) {
            prenotazioneList.add(prenotazioneMapper.prenotazioneDTOToPrenotazione(x));
        }

        return new ResponseEntity<>(prenotazioneList, HttpStatus.OK);
    }

    public ResponseEntity<Prenotazione> postPrenotazione(Prenotazione prenotazione) {
        log.info("Invocazione postPrenotazione()");

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        prenotazioneDTO = prenotazioneBL.validPrenotazionePost(prenotazioneDTO);

        prenotazioneDTO = prenotazioneBL.postPrenotazione(prenotazioneDTO);

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePrenotazione(Long idPrenotazione) {
        log.info("Invocazione deletePrenotazione()");

        prenotazioneBL.deletePrenotazione(idPrenotazione);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Prenotazione> putPrenotazione(Long idPrenotazione, Prenotazione prenotazione) {
        log.info("Invocazione putPrenotazione()");

        PrenotazioneDTO prenotazioneDTO = prenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);

        prenotazioneDTO = prenotazioneBL.validPrenotazionePut(prenotazioneDTO, idPrenotazione);

        prenotazioneDTO = prenotazioneBL.putPrenotazione(idPrenotazione, prenotazioneDTO);

        return new ResponseEntity<>(prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO), HttpStatus.OK);
    }
}
