package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {

}
