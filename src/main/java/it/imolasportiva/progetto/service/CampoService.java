package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.repository.CampoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampoService {
    private final CampoRepository campoRepository;

    @Autowired
    public CampoService(CampoRepository campoRepository) {
        this.campoRepository = campoRepository;
    }

    public Optional<CampoEntity> findById(Long id) {
        return campoRepository.findById(id);
    }

    public List<CampoEntity> findCampiLiberiPost(LocalDateTime data, LocalDateTime dataFine, String tipologia) {
        return campoRepository.findCampiLiberiPost(data, dataFine, tipologia);
    }

    public List<CampoEntity> findCampiLiberiPut(LocalDateTime data, LocalDateTime dataFine, String tipologia, Long idPrenotazione) {
        return campoRepository.findCampiLiberiPut(data, dataFine, tipologia, idPrenotazione);
    }
}
