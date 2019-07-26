package com.transporteporto.apitransporteporto.controller;

import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.service.LinhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/linha")
@CrossOrigin(origins="*")
@Api(value="API REST Linha Controller")
public class LinhaController {

    private final LinhaService linhaService;

    @Autowired
    public LinhaController(LinhaService linhaService) {
        this.linhaService = linhaService;
    }

    @GetMapping("/list")
    @ApiOperation(value="Listagem de Linha de Ônibus.")
    ResponseEntity<List<LinhaDTO>> list() {

        try {
            List<LinhaDTO> lista = linhaService.findAll();

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foram encontradas linhas de ônibus.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException("Não foram encontradas linhas de ônibus - Error: " + e.getMessage());
        }

    }

}
