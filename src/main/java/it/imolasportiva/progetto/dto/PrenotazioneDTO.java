package it.imolasportiva.progetto.dto;

import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.UtenteEntity;
import java.util.Date;

public class PrenotazioneDTO {
    private Long id;
    private UtenteEntity idUtentePrenotato;
    private int durataPrenotazioneOre;
    private int durataDeadlineCancellazione;
    private Date dataPrenotazione;
    private int numeroPartecipanti;
    private int quota;
    private CampoEntity campo;

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

    public CampoEntity getCampo() {
        return campo;
    }

    public void setCampo(CampoEntity campo) {
        this.campo = campo;
    }
}
