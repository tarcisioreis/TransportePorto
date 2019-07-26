package com.transporteporto.apitransporteporto.controller;

import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.service.LinhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/linha")
@CrossOrigin(origins="*")
@Api(value="API REST Linha Controller")
public class LinhaController {

    private final LinhaService linhaService;

    private List<LinhaDTO> lista;

    @Autowired
    public LinhaController(LinhaService linhaService) {
        this.linhaService = linhaService;
    }

    @GetMapping("/list")
    @ApiOperation(value="Listagem de Linha de Ônibus.")
    ResponseEntity<List<LinhaDTO>> list() {

        try {
            lista = linhaService.findAll();

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foram encontradas linhas de ônibus.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @PostMapping("/buscarPorNome/{nome}")
    @ApiOperation(value="Busca de Linha de Ônibus por Nome.")
    ResponseEntity<List<LinhaDTO>> buscarPorHome(@Valid @PathVariable String nome) {

        try {
            lista = linhaService.findByName(nome);

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foi encontrada linha de ônibus.");
            }

            return new ResponseEntity<>(lista, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @PostMapping("/save")
    @ApiOperation(value="Salva Linha de Ônibus por Nome.")
    ResponseEntity<LinhaDTO> save(@Valid @RequestBody LinhaDTO linhaDTO) {

        try {
            boolean found = linhaService.existByCodeAndName(linhaDTO);

            if (found) {
                throw new BusinessException("Linha já cadastrada. Verifique o código ou nome informados.");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(LinhaDTO.valueOf(linhaService.save(linhaDTO)), HttpStatus.OK);
    }

}
