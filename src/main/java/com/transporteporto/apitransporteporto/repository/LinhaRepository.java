package com.transporteporto.apitransporteporto.repository;

import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.entity.Linha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LinhaRepository extends CrudRepository<Linha, Long> {

    boolean existsByCodigo(String codigo);
    boolean existsByNome(String nome);

}
