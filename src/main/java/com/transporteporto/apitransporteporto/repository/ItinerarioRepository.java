package com.transporteporto.apitransporteporto.repository;

import com.transporteporto.apitransporteporto.entity.Itinerario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItinerarioRepository extends CrudRepository<Itinerario, Long> {

    boolean existsByIdLinha(String idLinha);
}
