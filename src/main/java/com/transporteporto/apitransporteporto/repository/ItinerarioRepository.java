package com.transporteporto.apitransporteporto.repository;

import com.transporteporto.apitransporteporto.dto.ItinerarioDTO;
import com.transporteporto.apitransporteporto.entity.Itinerario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItinerarioRepository extends CrudRepository<Itinerario, Long> {

    @Query("SELECT new com.transporteporto.apitransporteporto.dto.ItinerarioDTO(it.id, it.latitude, it.longitude, it.idLinha) " +
            "FROM Itinerario it " +
            "WHERE it.idLinha = :idLinha")
    ItinerarioDTO findByLinha(Long idLinha);

    boolean existsByIdLinha(Long idLinha);
}
