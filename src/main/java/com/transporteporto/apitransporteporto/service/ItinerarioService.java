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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;

    @Autowired
    public ItinerarioService(ItinerarioRepository itinerarioRepository) {
        this.itinerarioRepository = itinerarioRepository;
    }

    public List<ItinerarioDTO> findByIdLinha(String idLinha) {

        OkHttpClient httpClient = new OkHttpClient();
        Response response = null;
        List<ItinerarioDTO> lista = new ArrayList<ItinerarioDTO>();
        String retorno = null;

        Request request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_LIST_ITINERARIO_POR_LINHA + idLinha)
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            retorno = response.body().string();

            JSONObject jsonObjectLinha = null;
            JSONObject jsonObjectRouter = null;
            JSONArray jsonArrayRouter = new JSONArray();
            HashMap<String, Object> map = null;

            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(retorno, typeRef);

            for(Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();

                if (entry.getValue() instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> value = (LinkedHashMap<String, Object>) entry.getValue();

                    for(Map.Entry<String, Object> inner : value.entrySet()) {
                        String innerKey = inner.getKey();
                        Object values = inner.getValue();

                        if (innerKey.equals("lat") || innerKey.equals("lng")) {
                            if (jsonObjectRouter == null) {
                                jsonObjectRouter = new JSONObject();
                            }

                            jsonObjectRouter.put(innerKey, values);
                            jsonArrayRouter.put(jsonObjectRouter);

                            jsonObjectRouter = null;
                        }
                    }
                }
            }

            if (jsonArrayRouter != null) {
                jsonObjectLinha = new JSONObject();

                jsonObjectLinha.put("idlinha", idLinha);

                jsonObjectLinha.put("Routers", jsonArrayRouter);
            }

            JSONObject jsonObject = null;
            for (int i = 0; jsonArrayRouter.length() > i; i++) {
                jsonObject = jsonArrayRouter.getJSONObject(i);

                ItinerarioDTO itinerarioDTO = new ItinerarioDTO();

                itinerarioDTO.setIdLinha(jsonObjectLinha.getLong("idlinha"));

                if (jsonObject.has("lat")) {
                    itinerarioDTO.setLatitude(jsonObject.getDouble("lat"));
                }

                if (jsonObject.has("lng")) {
                    itinerarioDTO.setLongitude(jsonObject.getDouble("lng"));
                }

                lista.add(itinerarioDTO);
            }

            return lista;
        } catch (Exception e) {
            return null;
        }

    }

//    public String findByIdLinhaDataBank(String idLinha) {
//
//    }

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
