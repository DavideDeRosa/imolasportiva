package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.repository.CampoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampoService {
    private final CampoRepository campoRepository;

    @Autowired
    public CampoService(CampoRepository campoRepository) {
        this.campoRepository = campoRepository;
    }

    public Optional<CampoEntity> findById(Long id){
        return campoRepository.findById(id);
    }

    public List<CampoEntity> findCampiLiberi(Date data, String tipologia){
        return campoRepository.findCampiLiberi(data, tipologia);
    }
}
