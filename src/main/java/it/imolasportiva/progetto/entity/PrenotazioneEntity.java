package it.imolasportiva.progetto.entity;

import it.imolasportiva.progetto.utils.Campo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
