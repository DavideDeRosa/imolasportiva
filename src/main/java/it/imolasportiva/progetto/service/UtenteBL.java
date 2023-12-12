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
            return null; //forse si potrebbe gestire diversamente questo caso!
        }

        UtenteDTO utenteDTO = utenteMapper.utenteEntityToUtenteDTO(utente.get());
        return utenteDTO;
    }

    public UtenteEntity postUtente(UtenteDTO utenteDTO){
        UtenteEntity utenteEntity = utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
        utenteService.saveUtente(utenteEntity);
        return utenteEntity;
    }

    //giusto come metodo? a livello logico
    //oppure bisogna fare i due passaggi direttamente nel controller delle API?
    public Utente fromUtenteEntityToUtente(UtenteEntity utenteEntity){
        UtenteDTO utenteDTO = utenteMapper.utenteEntityToUtenteDTO(utenteEntity);
        Utente utente = utenteMapper.utenteDTOToUtente(utenteDTO);
        return utente;
    }
}
