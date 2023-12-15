package it.imolasportiva.progetto.service;

import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.mapper.UtenteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UtenteBL {

    private final UtenteService utenteService;
    private final UtenteMapper utenteMapper;

    @Autowired
    public UtenteBL(UtenteService utenteService, UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.utenteMapper = utenteMapper;
    }

    public UtenteDTO getUtenteDTObyId(Long id){
        Optional<UtenteEntity> utente = utenteService.findById(id);
        if(!utente.isPresent()){
            return null;
        }

        return utenteMapper.utenteEntityToUtenteDTO(utente.get());
    }

    public UtenteDTO postUtente(UtenteDTO utenteDTO){
        UtenteEntity utenteEntity = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
        utenteEntity = utenteService.saveUtente(utenteEntity);
        return utenteMapper.utenteEntityToUtenteDTO(utenteEntity);
    }

    public UtenteDTO putUtente(Long id, UtenteDTO utenteDTO){
        Optional<UtenteEntity> utente = utenteService.findById(id);
        if(!utente.isPresent()){
            return null;
        }

        utenteDTO.setId(id);
        UtenteEntity utenteEntity = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
        utenteEntity = utenteService.updateUtente(utenteEntity);

        return utenteMapper.utenteEntityToUtenteDTO(utenteEntity);
    }

    public void deleteUtente(Long id){
        if(getUtenteDTObyId(id) != null){
            utenteService.deleteUtente(id);
        }
    }
}
