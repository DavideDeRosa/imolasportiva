package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {

    @Query("SELECT p FROM PrenotazioneEntity p WHERE FUNCTION('EXTRACT', YEAR FROM p.dataPrenotazione) = :anno")
    List<PrenotazioneEntity> findByYear(@Param("anno") int anno);

    @Query("SELECT p FROM PrenotazioneEntity p WHERE FUNCTION('EXTRACT', YEAR FROM p.dataPrenotazione) = :anno AND FUNCTION('EXTRACT', MONTH FROM p.dataPrenotazione) = :mese")
    List<PrenotazioneEntity> findByYearAndMonth(@Param("anno") int anno, @Param("mese") int mese);

    @Query("SELECT p FROM PrenotazioneEntity p WHERE p.campo.id = :campo AND ((p.dataPrenotazione >= :start AND p.dataPrenotazione < :finish) OR (p.dataFinePrenotazione > :start AND p.dataFinePrenotazione <= :finish))")
    List<PrenotazioneEntity> findCampoOccupatoPost(@Param("campo") Long campo, @Param("start") LocalDateTime start, @Param("finish") LocalDateTime finish);

    @Query("SELECT p FROM PrenotazioneEntity p WHERE p.campo.id = :campo AND ((p.dataPrenotazione >= :start AND p.dataPrenotazione < :finish) OR (p.dataFinePrenotazione > :start AND p.dataFinePrenotazione <= :finish)) AND p.id <> :idP")
    List<PrenotazioneEntity> findCampoOccupatoPut(@Param("campo") Long campo, @Param("start") LocalDateTime start, @Param("finish") LocalDateTime finish, @Param("idP") Long idP);
}