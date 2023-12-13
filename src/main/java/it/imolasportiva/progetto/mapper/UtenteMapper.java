package it.imolasportiva.progetto.mapper;

import imolasportiva.model.Utente;
import it.imolasportiva.progetto.dto.UtenteDTO;
import it.imolasportiva.progetto.entity.UtenteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    @Mapping(target = "dataNascita", expression = "java(convertStringToDate(utente.getDataNascita()))")
    UtenteDTO utenteToUtenteDTO(Utente utente);

    UtenteEntity utenteDTOToUtenteEntity(UtenteDTO utenteDTO);

    Utente utenteDTOToUtente(UtenteDTO utenteDTO);

    UtenteDTO utenteEntityToUtenteDTO(UtenteEntity utenteEntity);

    default Date convertStringToDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Error converting String to Date");
        }
    }
}
