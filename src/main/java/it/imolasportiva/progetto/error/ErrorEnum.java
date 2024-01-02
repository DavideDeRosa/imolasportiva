package it.imolasportiva.progetto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    UTENTENOTFOUND("UtenteNotFound", "Utente non presente!", 404),
    PRENOTAZIONENOTFOUND("PrenotazioneNotFound", "Prenotazione non presente!", 404),
    ANNONOTFOUND("AnnoNotFound", "Filtro errato! Non puoi richiedere un filtro per il mese senza un anno specifico!", 400),
    MESEERROR("MeseError", "Mese non compreso tra 1 e 12!", 400),
    PRENOTAZIONEWRONGTIME("PrenotazioneWrongTime", "La prenotazione è errata! Può essere eseguita una prenotazione tutti i giorni dalle 8 alle 24, tranne il Martedì!", 400),
    CAMPONOTFOUND("CampoNotFound", "Campo non presente!", 404),
    CAMPONOTAVAILABLE("CampoNotAvailable", "Campo non disponibile per la prenotazione in quell'ora!", 400),
    CAMPOERROR("CampoError", "Campo non corretto!", 400),
    NUMEROPARTECIPANTIERROR("NumeroPartecipantiError", "Il numero di partecipanti non è corretto! (2,4,10)", 400);


    private String code;
    private String message;
    private int httpStatus;
}
