package it.imolasportiva.progetto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    UtenteNotFound("UtenteNotFound", "Utente non presente!", 404),
    PrenotazioneNotFound("PrenotazioneNotFound", "Prenotazione non presente!", 404),
    AnnoNotFound("AnnoNotFound", "Filtro errato! Non puoi richiedere un filtro per il mese senza un anno specifico!", 400),
    MeseError("MeseError", "Mese non compreso tra 1 e 12!", 400),
    PrenotazioneWrongTime("PrenotazioneWrongTime", "La prenotazione è errata! Può essere eseguita una prenotazione tutti i giorni dalle 8 alle 24, tranne il Martedì!", 400);


    private String code;
    private String message;
    private int httpStatus;
}
