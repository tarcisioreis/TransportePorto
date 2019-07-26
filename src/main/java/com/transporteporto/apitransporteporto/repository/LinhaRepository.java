package com.transporteporto.apitransporteporto.repository;

import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.entity.Linha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LinhaRepository extends CrudRepository<Linha, Long> {

//    public abstract Iterable<Linha> findAll();
//
//    public List<LinhaDTO> findAllLinhas() {
//        List<Linha> lista = (List<Linha>) this.findAll();
//        List<LinhaDTO> listaDTO = new ArrayList<LinhaDTO>();
//
//        for(Linha linha : lista) {
//            listaDTO.add(new LinhaDTO(linha.getId(), linha.getCodigo(), linha.getNome()));
//        }
//
//        return listaDTO;
//    }
}
