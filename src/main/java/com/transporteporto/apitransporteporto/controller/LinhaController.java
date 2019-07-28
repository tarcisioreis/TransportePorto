package com.transporteporto.apitransporteporto.controller;

import com.transporteporto.apitransporteporto.dto.LinhaDTO;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.service.LinhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
    private LinhaDTO dto;
    private boolean found;
    private String message;

    @Autowired
    public LinhaController(LinhaService linhaService) {
        this.linhaService = linhaService;
    }

    @GetMapping("/list")
    @ApiOperation(value="Listagem de Todas as Linha de Ônibus.")
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

    @PostMapping("/create")
    @ApiOperation(value="Criação de dados da Linha de Ônibus.")
    ResponseEntity<LinhaDTO> create(@Valid @RequestBody LinhaDTO linhaDTO) {

        try {
            if ((message = linhaService.validarAtributos(linhaDTO, "CREATE")) != null) {
                throw new BusinessException(message);
            }

            found = linhaService.existByCodeAndName(linhaDTO);

            if (found) {
                throw new BusinessException("Linha já cadastrada. Verifique o código ou nome informados.");
            }

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(LinhaDTO.valueOf(linhaService.save(linhaDTO)), HttpStatus.CREATED);

    }

    @PutMapping("/update")
    @ApiOperation(value="Alteração de dados de Linha de Ônibus.")
    ResponseEntity<LinhaDTO> update(@Valid @RequestBody LinhaDTO linhaDTO) {

        try {
            if ((message = linhaService.validarAtributos(linhaDTO, "UPDATE")) != null) {
                throw new BusinessException(message);
            }

            dto = new LinhaDTO();
            BeanUtils.copyProperties(linhaDTO, dto);

            if (!linhaService.findById(dto.getId()).isPresent()) {
                throw new BusinessException("Não foi encontrada Linha de Ônibus. Verifique os dados informados.");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(LinhaDTO.valueOf(linhaService.save(dto)), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="Exclusão de Linha de Ônibus.")
    ResponseEntity<String> delete(@Valid @PathVariable Long id) {

        try {
            if (!linhaService.findById(id).isPresent()) {
                throw new BusinessException("Não foi encontrada Linha de Ônibus. Verifique os dados informados.");
            } else {
                linhaService.delete(id);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>("Linha de Ônibus excluida com sucesso.", HttpStatus.OK);

    }

}
