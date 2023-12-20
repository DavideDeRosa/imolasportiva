package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public Optional<PrenotazioneEntity> findById(Long id){
        return prenotazioneRepository.findById(id);
    }

    public PrenotazioneEntity savePrenotazione(PrenotazioneEntity prenotazioneEntity){
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public PrenotazioneEntity updatePrenotazione(PrenotazioneEntity prenotazioneEntity){
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public void deletePrenotazione(Long id){
        prenotazioneRepository.deleteById(id);
    }

    public List<PrenotazioneEntity> findAll(){
        return prenotazioneRepository.findAll();
    }

    public List<PrenotazioneEntity> findByYear(int anno){
        return prenotazioneRepository.findByYear(anno);
    }

    public List<PrenotazioneEntity> findByYearAndMonth(int anno, int mese){
        return prenotazioneRepository.findByYearAndMonth(anno, mese);
    }

    public List<PrenotazioneEntity> findCampoOccupatoPost(Long campo, Date data, int durata){
        return prenotazioneRepository.findCampoOccupatoPost(campo, data, durata);
    }

    public List<PrenotazioneEntity> findCampoOccupatoPut(Long campo, Date data, int durata, Long idPrenotazione){
        return prenotazioneRepository.findCampoOccupatoPut(campo, data, durata, idPrenotazione);
    }
}
