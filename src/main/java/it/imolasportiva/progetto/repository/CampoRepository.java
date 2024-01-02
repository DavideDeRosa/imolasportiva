package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.CampoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampoRepository extends JpaRepository<CampoEntity, Long> {

    @Query("SELECT c FROM CampoEntity c WHERE c.id NOT IN (SELECT p.campo.id FROM PrenotazioneEntity p WHERE ((p.dataPrenotazione >= :start AND p.dataPrenotazione < :finish) OR (p.dataFinePrenotazione > :start AND p.dataFinePrenotazione <= :finish))) AND c.tipologia = :tipologia")
    List<CampoEntity> findCampiLiberiPost(@Param("start") LocalDateTime start, @Param("finish") LocalDateTime finish, @Param("tipologia") String tipologia);

    @Query("SELECT c FROM CampoEntity c WHERE c.id NOT IN (SELECT p.campo.id FROM PrenotazioneEntity p WHERE ((p.dataPrenotazione >= :start AND p.dataPrenotazione < :finish) OR (p.dataFinePrenotazione > :start AND p.dataFinePrenotazione <= :finish)) AND p.id <> :idP) AND c.tipologia = :tipologia")
    List<CampoEntity> findCampiLiberiPut(@Param("start") LocalDateTime start, @Param("finish") LocalDateTime finish, @Param("tipologia") String tipologia, @Param("idP") Long idP);
}
