package it.imolasportiva.progetto.repository;

import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {
    @Query(value = "SELECT * FROM Prenotazione p WHERE EXTRACT(YEAR FROM p.dataprenotazione) = :anno", nativeQuery = true)
    List<PrenotazioneEntity> findByYear(@Param("anno") int anno);

    @Query(value = "SELECT * FROM Prenotazione p WHERE EXTRACT(YEAR FROM p.dataprenotazione) = :anno AND EXTRACT(MONTH FROM p.dataprenotazione) = :mese", nativeQuery = true)
    List<PrenotazioneEntity> findByYearAndMonth(@Param("anno") int anno, @Param("mese") int mese);
}
