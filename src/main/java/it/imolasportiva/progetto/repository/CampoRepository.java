package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.CampoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CampoRepository extends JpaRepository<CampoEntity, Long> {

    @Query(value = "SELECT * FROM campo WHERE id NOT IN (SELECT id_campo FROM prenotazione WHERE dataprenotazione = :data) AND tipologia = :tipologia", nativeQuery = true)
    List<CampoEntity> findCampiLiberi(@Param("data") Date data, @Param("tipologia") String tipologia);

}
