package it.imolasportiva.progetto.mapper;

import imolasportiva.model.Prenotazione;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.service.UtenteBL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class PrenotazioneMapper {
    @Autowired
    UtenteBL utenteBL;

    @Autowired
    UtenteMapper utenteMapper;

    @Mapping(target = "dataPrenotazione", expression = "java(convertStringToDate(prenotazione.getDataPrenotazione()))")
    abstract PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione);

    abstract PrenotazioneEntity prenotazioneDTOToPrenotazioneEntity(PrenotazioneDTO prenotazioneDTO);

    abstract Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO);

    abstract PrenotazioneDTO prenotazioneEntityToPrenotazioneDTO(PrenotazioneEntity prenotazioneEntity);

    Date convertStringToDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Error converting String to Date");
        }
    }

    UtenteEntity map(String idUtente) {
        UtenteDTO utenteDTO = utenteBL.getUtenteDTObyId(Long.valueOf(idUtente));
        return utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
    }

    String map(UtenteEntity utenteEntity) {
        return utenteEntity.getId().toString();
    }
}
