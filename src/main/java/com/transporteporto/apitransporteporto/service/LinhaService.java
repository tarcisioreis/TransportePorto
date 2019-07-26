package com.transporteporto.apitransporteporto.service;

import com.transporteporto.apitransporteporto.constantes.Constantes;
import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.repository.LinhaRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LinhaService {

    private final LinhaRepository linhaRepository;

    @Autowired
    public LinhaService(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    public List<LinhaDTO> findAll() {

        OkHttpClient httpClient = new OkHttpClient();
        Response response = null;
        List<LinhaDTO> lista = new ArrayList<LinhaDTO>();

        Request request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_LIST_LINHAS)
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            String retorno = response.body().string();

            JSONArray jsonArray = new JSONArray(retorno);

            JSONObject jsonObject = null;
            for (int i = 0; jsonArray.length() > i; i++) {
                jsonObject = jsonArray.getJSONObject(i);

                lista.add(new LinhaDTO(jsonObject.getLong("id"),
                        jsonObject.getString("codigo"),
                        jsonObject.getString("nome")));
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return lista;
    }

}
