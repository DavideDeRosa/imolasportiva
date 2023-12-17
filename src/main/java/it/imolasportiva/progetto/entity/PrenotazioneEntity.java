package it.imolasportiva.progetto.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prenotazione")
public class PrenotazioneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private UtenteEntity idUtentePrenotato;

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
    campo diventa interfaccia, con specializ. diversi tipi di campo (calcio tennis ecc):
        - id
        - codice campo (Come precendenza CAMPO1 ecc)
        - tipologia campo

    il campo deve essere libero (controllo con query se il campo ha gia prenotazione in quel momento),
    gestione del numero di partecipanti (se 2 o 4 prenoto tennis, altrimenti 10 prenoto calcio, errore in caso diverso),
    in caso in cui il campo non venga specificato si prende il primo disponibile
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UtenteEntity getIdUtentePrenotato() {
        return idUtentePrenotato;
    }

    public void setIdUtentePrenotato(UtenteEntity idUtentePrenotato) {
        this.idUtentePrenotato = idUtentePrenotato;
    }

    public int getDurataPrenotazioneOre() {
        return durataPrenotazioneOre;
    }

    public void setDurataPrenotazioneOre(int durataPrenotazioneOre) {
        this.durataPrenotazioneOre = durataPrenotazioneOre;
    }

    public int getDurataDeadlineCancellazione() {
        return durataDeadlineCancellazione;
    }

    public void setDurataDeadlineCancellazione(int durataDeadlineCancellazione) {
        this.durataDeadlineCancellazione = durataDeadlineCancellazione;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        this.numeroPartecipanti = numeroPartecipanti;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}
