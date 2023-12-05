package it.imolasportiva.progetto.repository;

import imolasportiva.model.UtenteEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UtenteRepository{
    private static final Map<String, UtenteEntity> map = new HashMap<>();

    public UtenteEntity getUtente(String id){
        return map.get(id);
    }
    public UtenteEntity putUtente(UtenteEntity utenteEntity){
        map.put(utenteEntity.getId(), utenteEntity);
        return utenteEntity;
    }
}
