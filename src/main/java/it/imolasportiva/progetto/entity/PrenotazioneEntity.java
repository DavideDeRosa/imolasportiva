package it.imolasportiva.progetto.entity;

import it.imolasportiva.progetto.utils.Campo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prenotazione")
public class PrenotazioneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_utente")
    private Long idUtentePrenotato;

    @Column(name = "durataprenotazione")
    private int durataPrenotazioneOre;

    @Column(name = "duratadeadlinecancellazione")
    private int durataDeadlineCancellazione; // quante ore prima si puo cancellare

    @Column(name = "dataprenotazione")
    private Date dataPrenotazione;

    @Column(name = "numeropartecipanti")
    private int numeroPartecipanti;

    @Column(name = "quota")
    private int quota;
    //private Campo campo; //opzionale

    /*
    prenotazione aggiungiamo:
        - Date giorno prenotazione e ora prenotazione
        - numero partecipanti
        - quota da pagare totale

    campo diventa interfaccia, con specializ. diversi tipi di campo (calcio tennis ecc):
        - id
        - codice campo (Come precendenza CAMPO1 ecc)
        - tipologia campo

    vanno quindi aggiunte BL dove controllo l'esistenza di un utente,
    il campo deve essere libero (controllo con query se il campo ha gia prenotazione in quel momento),
    gestione del numero di partecipanti (se 2 o 4 prenoto tennis, altrimenti 10 prenoto calcio, errore in caso diverso),
    in caso in cui il campo non venga specificato si prende il primo disponibile,
    vincolo orario dalle 8 alle 24, martedi chiusi sempre.
     */
}
