package it.imolasportiva.progetto.mapper;

import imolasportiva.model.Prenotazione;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.service.UtenteBL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class PrenotazioneMapper {
    @Autowired
    UtenteBL utenteBL;

    @Autowired
    UtenteMapper utenteMapper;

    @Mappings({
            @Mapping(target = "dataPrenotazione", expression = "java(convertStringToDate(prenotazione.getDataPrenotazione()))"),
            @Mapping(source = "durataDeadlineCancellazioneOre", target = "durataDeadlineCancellazione")
    })
    public abstract PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione);

    public abstract PrenotazioneEntity prenotazioneDTOToPrenotazioneEntity(PrenotazioneDTO prenotazioneDTO);

    @Mapping(source = "durataDeadlineCancellazione", target = "durataDeadlineCancellazioneOre")
    public abstract Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO);

    public abstract PrenotazioneDTO prenotazioneEntityToPrenotazioneDTO(PrenotazioneEntity prenotazioneEntity);

    Date convertStringToDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Error converting String to Date");
        }
    }

    UtenteEntity map(Long idUtente) {
        UtenteDTO utenteDTO = utenteBL.getUtenteDTObyId(idUtente);
        return utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
    }

    Long map(UtenteEntity utenteEntity) {
        return utenteEntity.getId();
    }
}
