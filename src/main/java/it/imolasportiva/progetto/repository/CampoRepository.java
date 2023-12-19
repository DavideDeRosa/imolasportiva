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

    @Query(value = "SELECT * FROM campo WHERE id NOT IN (SELECT id_campo FROM prenotazione WHERE ((dataprenotazione >= :data AND dataprenotazione < (CAST(:data AS timestamp) + :durata * INTERVAL '1 hour')) OR ((dataprenotazione + durataprenotazione * INTERVAL '1 hour') <= (CAST(:data AS timestamp) + :durata * INTERVAL '1 hour') AND (dataprenotazione + durataprenotazione * INTERVAL '1 hour') > :data))) AND tipologia = :tipologia", nativeQuery = true)
    List<CampoEntity> findCampiLiberi(@Param("data") Date data, @Param("tipologia") String tipologia, @Param("durata") int durata);

}
