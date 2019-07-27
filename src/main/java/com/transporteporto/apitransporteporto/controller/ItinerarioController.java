package com.transporteporto.apitransporteporto.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporteporto.apitransporteporto.constantes.Constantes;
import com.transporteporto.apitransporteporto.dto.ItinerarioDTO;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.service.ItinerarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/itinerario")
@CrossOrigin(origins="*")
@Api(value="API REST Itinerario Controller")
public class ItinerarioController {

    private final ItinerarioService itinerarioService;

    private List<ItinerarioDTO> lista;

    @Autowired
    public ItinerarioController(ItinerarioService itinerarioService) {
        this.itinerarioService = itinerarioService;
    }

    @PostMapping("/buscarPorLinha/{idlinha}")
    @ApiOperation(value="Listagem de Itinerário por determinada Linha.")
    ResponseEntity<List<ItinerarioDTO>> buscarPorLinha(@Valid @RequestParam(name = "idlinha") String idlinha) {

        try {
            lista = itinerarioService.findByIdLinha(idlinha);

            if (lista == null || lista.size() == 0) {
                throw new BusinessException("Não foram encontrados itineários para a linha de ônibus.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @PostMapping("/save")
    @ApiOperation(value="Salva de itinerario de Linha de Ônibus.")
    ResponseEntity<ItinerarioDTO> save(@Valid @RequestBody ItinerarioDTO itinerarioDTO) {

        try {
            boolean found = itinerarioService.existByItinerario(itinerarioDTO);

            if (found) {
                throw new BusinessException("Itinerario de Linha já cadastrado. Verifique os dados informados.");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(ItinerarioDTO.valueOf(itinerarioService.save(itinerarioDTO)), HttpStatus.OK);
    }

}
