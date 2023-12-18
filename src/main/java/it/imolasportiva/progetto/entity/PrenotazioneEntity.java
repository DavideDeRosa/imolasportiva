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

    @ManyToOne
    @JoinColumn(name = "id_campo")
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
