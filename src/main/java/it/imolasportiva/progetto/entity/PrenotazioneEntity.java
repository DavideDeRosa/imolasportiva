package it.imolasportiva.progetto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
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
}
