package it.imolasportiva.progetto.entity;

import java.util.Date;

//@Entity
public class UtenteEntity {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private Long telefono;
}
