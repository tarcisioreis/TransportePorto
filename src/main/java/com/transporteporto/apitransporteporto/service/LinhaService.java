package com.transporteporto.apitransporteporto.service;

import com.transporteporto.apitransporteporto.constantes.Constantes;
import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.entity.Linha;
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

    private OkHttpClient httpClient;
    private Response response;
    private List<LinhaDTO> lista;
    private LinhaDTO linhaDTO;
    private Request request;

    @Autowired
    public LinhaService(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    public List<LinhaDTO> findAll() {

        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<LinhaDTO>();

        request = new Request.Builder()
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

    public List<LinhaDTO> findByName(String name) {

        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<LinhaDTO>();

        request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_FIND_LINHA_POR_NOME + name)
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

    public List<LinhaDTO> findByCode(String code) {

        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<LinhaDTO>();

        request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_FIND_LINHA_POR_NOME + code)
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

    public boolean existByCodeAndName(LinhaDTO linhaDTO) {
        boolean foundName = (this.findByName(linhaDTO.getNome()).size() > 0 ? true : false);
        boolean foundCode = (this.findByCode(linhaDTO.getCodigo()).size() > 0 ? true : false);

        if (foundName) {
            return foundName;   // Encontrou um nome parecido
        } else if (foundCode) {
            return foundCode;   // Encontrou um codigo parecido
        } else if (this.existsByCodigo(linhaDTO.getCodigo())) { // Procura no banco
            return true;
        } else if (this.existsByNome(linhaDTO.getNome())) {     // Procura no banco
            return true;
        } else {
            return false;   // Nenhum valor foi encontrado igual a codigo e nome da linha
        }

    }

    public boolean existsByCodigo(String codigo) {
        return linhaRepository.existsByCodigo(codigo);
    }

    public boolean existsByNome(String nome) {
        return linhaRepository.existsByNome(nome);
    }

    public Linha save(LinhaDTO linhaDTO) {
        return linhaRepository.save(linhaDTO.valueOf());
    }

}
