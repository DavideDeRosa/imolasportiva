package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.UtenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<UtenteEntity, Long> {

}
