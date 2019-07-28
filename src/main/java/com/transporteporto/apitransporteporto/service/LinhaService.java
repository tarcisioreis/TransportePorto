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
import java.util.Optional;

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

    /*
        Busca todas as linhas de ônibus
     */
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

    /*
        Busca linha de ônibus por ID
     */
    public Optional<Linha> findById(Long id) {

        Optional<Linha> linha = null;

        try {
            linha = linhaRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return linha;
    }

    /*
        Busca linha de ônibus por NOME
     */
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
            if (lista.size() == 0) {
                lista = this.findByNome(name);
            }

            if (lista.size() == 0) {
                return Collections.emptyList();
            }
        }

        return lista;
    }

    public List<LinhaDTO> findByNome(String name) {
        return linhaRepository.findByNome(name);
    }

    /*
        Busca linha de ônibus por CODIGO
     */
    public List<LinhaDTO> findByCode(String code) {

        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<LinhaDTO>();

        request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.ENDPOINT_FIND_LINHA_POR_CODIGO + code)
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

    /*
        Verifica na integração e banco de dados a existência ou não de linha de ônibus
     */
    public boolean existByCodeAndName(LinhaDTO linhaDTO) {
        boolean foundName = (this.findByName(linhaDTO.getNome()).size() > 0 ? true : false);
        boolean foundCode = (this.findByCode(linhaDTO.getCodigo()).size() > 0 ? true : false);
        boolean foundNameDataBank = (this.existsByNome(linhaDTO.getNome()) ? true : false);
        boolean foundCodeDataBank = (this.existsByCodigo(linhaDTO.getCodigo()) ? true : false);

        if (foundName) {
            return foundName;   // Encontrou um nome parecido
        } else if (foundCode) {
            return foundCode;   // Encontrou um codigo parecido
        } else if (foundNameDataBank) { // Procura no banco
            return foundNameDataBank;
        } else if (foundCodeDataBank) { // Procura no banco
            return foundCodeDataBank;
        } else {
            return false;   // Nenhum valor foi encontrado igual a codigo e nome da linha
        }

    }

    /*
        Verifica se codigo de linha de ônibus existe
     */
    public boolean existsByCodigo(String codigo) {
        return linhaRepository.existsByCodigo(codigo);
    }

    /*
        Verifica se nome de linha de ônibus existe
     */
    public boolean existsByNome(String nome) {
        return linhaRepository.existsByNome(nome);
    }

    /*
        Método usado para CREATE - via POST e UPDATE - via PUT
     */
    public Linha save(LinhaDTO linhaDTO) {
        return linhaRepository.save(linhaDTO.valueOf());
    }

    /*
        Método usado para DELETE - via DELETE
     */
    public void delete(Long id) {
        Linha linha = this.findById(id).get();
        linhaRepository.delete(linha);
    }

    public String validarAtributos(LinhaDTO linhaDTO, String operacao) {

        String message = null;

        try {

            if (operacao.equals("UPDATE")) {
                if (linhaDTO.getId() <= 0) {
                    message = "Informe ID da Linha.";
                }
            }
            if ((linhaDTO.getCodigo().isEmpty() || linhaDTO.getCodigo().length() == 0) && message == null) {
                message = "Informe CODIGO da Linha.";
            }
            if ((linhaDTO.getNome().isEmpty() || linhaDTO.getNome().length() == 0) && message == null) {
                message = "Informe NOME da Linha.";
            }
        } catch (Exception e) {
            message = e.getMessage();
        }

        return message;
    }

}
