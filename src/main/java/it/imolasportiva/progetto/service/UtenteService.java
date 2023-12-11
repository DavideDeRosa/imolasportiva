package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService{

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Optional<UtenteEntity> findById(Long id){
        return utenteRepository.findById(id);
    }

    public UtenteEntity saveUtente(UtenteEntity utenteEntity){
        return utenteRepository.save(utenteEntity);
    }

    public UtenteEntity updateUtente(UtenteEntity utenteEntity){
        return utenteRepository.save(utenteEntity);
    }

    public void deleteUtente(Long id){
        utenteRepository.deleteById(id);
    }
}
