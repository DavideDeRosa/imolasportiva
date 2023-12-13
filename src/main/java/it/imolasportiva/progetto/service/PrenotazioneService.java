package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PrenotazioneEntity saveUtente(PrenotazioneEntity prenotazioneEntity){
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public PrenotazioneEntity updateUtente(PrenotazioneEntity prenotazioneEntity){
        return prenotazioneRepository.save(prenotazioneEntity);
    }

    public void deleteUtente(Long id){
        prenotazioneRepository.deleteById(id);
    }
}
