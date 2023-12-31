package it.imolasportiva.progetto.mapper;

import imolasportiva.model.Prenotazione;
import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import it.imolasportiva.progetto.error.ErrorEnum;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.service.CampoService;
import it.imolasportiva.progetto.service.UtenteBL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PrenotazioneMapper {
    @Autowired
    UtenteBL utenteBL;

    @Autowired
    UtenteMapper utenteMapper;

    @Autowired
    CampoService campoService;

    @Mappings({
            @Mapping(target = "dataPrenotazione", expression = "java(convertStringToDate(prenotazione.getDataPrenotazione()))"),
            @Mapping(target = "dataFinePrenotazione", expression = "java(convertStringToDate(prenotazione.getDataFinePrenotazione()))"),
            @Mapping(source = "durataDeadlineCancellazioneOre", target = "durataDeadlineCancellazione"),
            @Mapping(source = "idCampo", target = "campo")
    })
    public abstract PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione);

    public abstract PrenotazioneEntity prenotazioneDTOToPrenotazioneEntity(PrenotazioneDTO prenotazioneDTO);

    @Mappings({
            @Mapping(source = "durataDeadlineCancellazione", target = "durataDeadlineCancellazioneOre"),
            @Mapping(source = "campo", target = "idCampo")
    })
    public abstract Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO);

    public abstract PrenotazioneDTO prenotazioneEntityToPrenotazioneDTO(PrenotazioneEntity prenotazioneEntity);

    LocalDateTime convertStringToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return LocalDateTime.parse(date, formatter);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error converting String to LocalDateTime");
        }
    }

    UtenteEntity map(Long idUtente) {
        UtenteDTO utenteDTO = utenteBL.getUtenteDTObyId(idUtente);
        return utenteMapper.utenteDTOToUtenteEntity(utenteDTO);
    }

    Long map(UtenteEntity utenteEntity) {
        return utenteEntity.getId();
    }

    CampoEntity mapC(Long id) {
        if (id == 0) {
            return null;
        } else {
            Optional<CampoEntity> campo = campoService.findById(id);
            if (!campo.isPresent()) {
                throw new ErrorException(ErrorEnum.CAMPONOTFOUND);
            }

            return campo.get();
        }
    }

    Long mapC(CampoEntity campoEntity) {
        return campoEntity.getId();
    }
}
