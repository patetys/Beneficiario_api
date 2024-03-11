package com.beneficiarioapi.controller;

import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.service.BeneficiarioService;
import com.beneficiarioapi.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/beneficiarios",produces = {"application/json"})
@Tag(name = "open-api")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;
    private final DocumentoService documentoService;
    private final ModelMapper modelMapper;

    public BeneficiarioController(BeneficiarioService beneficiarioService, DocumentoService documentoService, ModelMapper modelMapper) {
        this.beneficiarioService = beneficiarioService;
        this.documentoService = documentoService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Realiza o upload de arquivos", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping()
    public ResponseEntity<BeneficiarioDTO> cadastrarBeneficiario(@RequestBody @Valid BeneficiarioDTO BeneficiarioDto) {
        BeneficiarioDTO beneficiarioDTO = beneficiarioService.cadastrarBeneficiario(BeneficiarioDto);
        return new ResponseEntity<>(beneficiarioDTO,HttpStatus.CREATED);
    }

    @Operation(summary = "Busca dados de profissionais por idade e cargo exercido", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping()
    public ResponseEntity<List<BeneficiarioDTO>> listarBeneficiarios() {
        List<BeneficiarioDTO> listaBeneficiarios = beneficiarioService.listarBeneficiarios();

        if(listaBeneficiarios.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(listaBeneficiarios);
    }

    @Operation(summary = "Busca dados de profissionais por idade e cargo exercido", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<DocumentoDTO>> listarDocumentosPorIdBeneficiario(@PathVariable Long id) {
        List<DocumentoDTO> listaDocumetos = documentoService.listarDocumentosPorIdBeneficiario(id);

        if(listaDocumetos.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaDocumetos);
    }

    @Operation(summary = "Realiza o upload de arquivos", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioDTO> atualizarBeneficiario(@PathVariable long id,  @RequestBody @Valid BeneficiarioDTO beneficiarioDTO) {
        BeneficiarioDTO atualizaBeneficiario = beneficiarioService.atualizarBeneficiario(id,beneficiarioDTO);
        return ResponseEntity.ok(atualizaBeneficiario);
    }


    @Operation(summary = "Realiza o upload de arquivos", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerBeneficiario(@PathVariable("id") Long id) {
       beneficiarioService.removerBeneficiario(id);
       return ResponseEntity.noContent().build();
    }

}
