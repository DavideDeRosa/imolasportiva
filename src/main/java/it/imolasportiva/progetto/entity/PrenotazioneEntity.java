package it.imolasportiva.progetto.entity;

import it.imolasportiva.progetto.utils.Campo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PrenotazioneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUtentePrenotato;
    private int durataPrenotazioneOre;
    private int durataDeadlineCancellazione;
    private Campo campo;
}
