package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public Optional<PrenotazioneEntity> findById(Long id) {
        return prenotazioneRepository.findById(id);
    }

    public PrenotazioneEntity savePrenotazione(PrenotazioneEntity prenotazioneEntity) {
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public PrenotazioneEntity updatePrenotazione(PrenotazioneEntity prenotazioneEntity) {
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public void deletePrenotazione(Long id) {
        prenotazioneRepository.deleteById(id);
    }

    public List<PrenotazioneEntity> findAll() {
        return prenotazioneRepository.findAll();
    }

    public List<PrenotazioneEntity> findByYear(int anno) {
        return prenotazioneRepository.findByYear(anno);
    }

    public List<PrenotazioneEntity> findByYearAndMonth(int anno, int mese) {
        return prenotazioneRepository.findByYearAndMonth(anno, mese);
    }

    public List<PrenotazioneEntity> findCampoOccupatoPost(Long campo, LocalDateTime data, LocalDateTime dataFine) {
        return prenotazioneRepository.findCampoOccupatoPost(campo, data, dataFine);
    }

    public List<PrenotazioneEntity> findCampoOccupatoPut(Long campo, LocalDateTime data, LocalDateTime dataFine, Long idPrenotazione) {
        return prenotazioneRepository.findCampoOccupatoPut(campo, data, dataFine, idPrenotazione);
    }
}
