package com.transporteporto.apitransporteporto.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporteporto.apitransporteporto.constantes.Constantes;
import com.transporteporto.apitransporteporto.dto.ItinerarioDTO;
import com.transporteporto.apitransporteporto.entity.Itinerario;
import com.transporteporto.apitransporteporto.repository.ItinerarioRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;

    @Autowired
    public ItinerarioService(ItinerarioRepository itinerarioRepository) {
        this.itinerarioRepository = itinerarioRepository;
    }

    /*
        Busca Itinerarios de linhas de ônibus via integração PoaTransporte
     */
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

                if (jsonObjectRouter == null && (!key.equals("idlinha") &&
                        !key.equals("codigo")  &&
                        !key.equals("nome"))) {
                    jsonObjectRouter = new JSONObject();

                    jsonObjectRouter.put(key, entry.getValue());
                    jsonArrayRouter.put(jsonObjectRouter);

                    jsonObjectRouter = null;
                }

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
            ItinerarioDTO itinerarioDTO = null;
            for (int i = 0; jsonArrayRouter.length() > i; i++) {
                jsonObject = jsonArrayRouter.getJSONObject(i);

                if (itinerarioDTO == null) {
                    itinerarioDTO = new ItinerarioDTO();
                }

                itinerarioDTO.setIdLinha(jsonObjectLinha.getLong("idlinha"));

                if (jsonObject.has("lat")) {
                    itinerarioDTO.setLatitude(jsonObject.getDouble("lat"));
                }

                if (jsonObject.has("lng")) {
                    itinerarioDTO.setLongitude(jsonObject.getDouble("lng"));
                }

                if (!jsonObject.has("lat") && !jsonObject.has("lng")) {
                    String str = jsonObject.names().get(0).toString();
                    Long id = Long.valueOf(str) + 1;
                    itinerarioDTO.setId(id);
                }

                if (itinerarioDTO.getIdLinha()   != null &&
                    itinerarioDTO.getLatitude()  != null &&
                    itinerarioDTO.getLongitude() != null) {
                    lista.add(itinerarioDTO);

                    itinerarioDTO = null;
                }
            }

            return lista;
        } catch (Exception e) {
            return null;
        }

    }

    /*
        Verifica se itinerario já existe
     */
    public boolean existByItinerario(ItinerarioDTO itinerarioDTO) {
        List<ItinerarioDTO> lista = this.findByIdLinha(itinerarioDTO.getIdLinha().toString());

        boolean foundIntegracao = (lista.size() > 0 ? true : false);
        boolean found = true;

        if (foundIntegracao) {

            for(ItinerarioDTO dto : lista) {
                if (itinerarioDTO.getLatitude() != dto.getLatitude()) {
                    found = false;
                    break;
                }

                if (itinerarioDTO.getLongitude() != dto.getLongitude()) {
                    found = false;
                    break;
                }
            }

            foundIntegracao = found;
        }

        return foundIntegracao;
    }

//    public boolean existsByIdLinha(Long idLinha) {
//        return itinerarioRepository.existsByIdLinha(idLinha);
//    }

    /*
        Busca itinerario na base de dados
     */
    public ItinerarioDTO findByLinhaDataBank(ItinerarioDTO itinerarioDTO) {
        return itinerarioRepository.findByLinha(itinerarioDTO.getIdLinha());
    }

    /*
        Salva dados de novo itinerario na base de dados
     */
    public Itinerario save(ItinerarioDTO itinerarioDTO) {
        return itinerarioRepository.save(itinerarioDTO.valueOf());
    }

}
