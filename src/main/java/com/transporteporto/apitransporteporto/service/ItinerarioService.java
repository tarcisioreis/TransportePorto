package com.transporteporto.apitransporteporto.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporteporto.apitransporteporto.constantes.Constantes;
import com.transporteporto.apitransporteporto.dto.ItinerarioDTO;
import com.transporteporto.apitransporteporto.entity.Itinerario;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.repository.ItinerarioRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;

    @Autowired
    public ItinerarioService(ItinerarioRepository itinerarioRepository) {
        this.itinerarioRepository = itinerarioRepository;
    }

    public String findByIdLinha(String idLinha) {

        OkHttpClient httpClient = new OkHttpClient();
        Response response = null;
        //List<ItinerarioDTO> lista = new ArrayList<ItinerarioDTO>();
        String retorno = null;

        Request request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_LIST_ITINERARIO_POR_LINHA + idLinha)
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            retorno = response.body().string();

            return retorno;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean existByItinerario(ItinerarioDTO itinerarioDTO) {
        boolean foundIntegracao = (this.findByIdLinha(itinerarioDTO.getIdLinha().toString()) == null ? false : true);

        if (!foundIntegracao) {
            foundIntegracao = itinerarioRepository.existsByIdLinha(itinerarioDTO.getIdLinha().toString());
        }

        return foundIntegracao;
    }

    public Itinerario save(ItinerarioDTO itinerarioDTO) {
        return itinerarioRepository.save(itinerarioDTO.valueOf());
    }

}
