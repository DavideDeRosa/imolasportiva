package it.imolasportiva.progetto.repository;

import imolasportiva.model.Utente;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UtenteRepository{
    private static final Map<String, Utente> map = new HashMap<>();

    public Utente getUtente(String id){
        return map.get(id);
    }
    public Utente putUtente(Utente utenteEntity){
        map.put(utenteEntity.getId(), utenteEntity);
        return utenteEntity;
    }
}
