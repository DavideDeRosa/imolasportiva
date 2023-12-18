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
    PrenotazioneWrongTime("PrenotazioneWrongTime", "La prenotazione è errata! Può essere eseguita una prenotazione tutti i giorni dalle 8 alle 24, tranne il Martedì!", 400),
    CampoNotFound("CampoNotFound", "Campo non presente!", 404),
    CampoNotAvailable("CampoNotAvailable", "Campo non disponibile per la prenotazione in quell'ora!", 400),
    CampoError("CampoError", "Campo non corretto!", 400),
    NumeroPartecipantiError("NumeroPartecipantiError", "Il numero di partecipanti non è corretto! (2,4,10)", 400);


    private String code;
    private String message;
    private int httpStatus;
}
