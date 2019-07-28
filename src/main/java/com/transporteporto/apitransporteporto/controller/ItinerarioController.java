package com.transporteporto.apitransporteporto.controller;

import com.transporteporto.apitransporteporto.dto.ItinerarioDTO;
import com.transporteporto.apitransporteporto.exceptions.BusinessException;
import com.transporteporto.apitransporteporto.service.ItinerarioService;
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
@RequestMapping("/api/v1/itinerario")
@CrossOrigin(origins="*")
@Api(value="API REST Itinerario Controller")
public class ItinerarioController {

    private final ItinerarioService itinerarioService;

    private List<ItinerarioDTO> lista;
    private ItinerarioDTO dto;
    private boolean found;
    private String message;

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

    @PostMapping("/create")
    @ApiOperation(value="Criação de itinerario de Linha de Ônibus.")
    ResponseEntity<ItinerarioDTO> create(@Valid @RequestBody ItinerarioDTO itinerarioDTO) {

        try {
            if ((message = itinerarioService.validarAtributos(itinerarioDTO, "CREATE")) != null) {
                throw new BusinessException(message);
            }

            found = itinerarioService.existByItinerario(itinerarioDTO);
            dto = itinerarioService.findByLinhaDataBank(itinerarioDTO);

            boolean founddatabank = true;

            if (found) {
                throw new BusinessException("Itinerario de Linha já cadastrada, dados de localização já cadastrados. Verifique os dados informados.");
            } else if (dto != null) {
                if (dto.getLatitude() != itinerarioDTO.getLatitude()) {
                    founddatabank = false;
                }
                if (dto.getLongitude() != itinerarioDTO.getLongitude()) {
                    founddatabank = false;
                }
                if (!founddatabank) {
                    throw new BusinessException("Dados de localização já cadastrados. Verifique os dados informados.");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(ItinerarioDTO.valueOf(itinerarioService.save(itinerarioDTO)), HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value="Alteração de dados de Itinerario de Linha de Ônibus.")
    ResponseEntity<ItinerarioDTO> update(@Valid @RequestBody ItinerarioDTO itinerarioDTO) {

        try {
            if ((message = itinerarioService.validarAtributos(itinerarioDTO, "UPDATE")) != null) {
                throw new BusinessException(message);
            }

            dto = new ItinerarioDTO();
            BeanUtils.copyProperties(itinerarioDTO, dto);

            if (!itinerarioService.findById(dto.getId()).isPresent()) {
                throw new BusinessException("Não foi encontrado Itinerario de linha de ônibus. Verifique os dados informados.");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(ItinerarioDTO.valueOf(itinerarioService.save(dto)), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="Exclusão de Itinerario de Linha de Ônibus.")
    ResponseEntity<String> delete(@Valid @PathVariable Long id) {

        try {
            if (!itinerarioService.findById(id).isPresent()) {
                throw new BusinessException("Não foi encontrado Itinerario de Linha de Ônibus. Verifique os dados informados.");
            } else {
                itinerarioService.delete(id);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>("Itineario de Linha de Ônibus excluido com sucesso.", HttpStatus.OK);

    }

}
