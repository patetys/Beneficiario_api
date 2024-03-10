package com.beneficiarioapi.controller;

import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.service.BeneficiarioService;
import com.beneficiarioapi.service.DocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/beneficiarios")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;
    private final DocumentoService documentoService;
    private final ModelMapper modelMapper;

    public BeneficiarioController(BeneficiarioService beneficiarioService, DocumentoService documentoService, ModelMapper modelMapper) {
        this.beneficiarioService = beneficiarioService;
        this.documentoService = documentoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<BeneficiarioDTO> cadastrarBeneficiario(@RequestBody BeneficiarioDTO BeneficiarioDto) {
        BeneficiarioDTO beneficiarioDTO = beneficiarioService.cadastrarBeneficiario(BeneficiarioDto);
        return new ResponseEntity<>(beneficiarioDTO,HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<BeneficiarioDTO>> listarBeneficiarios() {
        List<BeneficiarioDTO> listaBeneficiarios = beneficiarioService.listarBeneficiarios();

        if(listaBeneficiarios.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(listaBeneficiarios);
    }

    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<DocumentoDTO>> listarDocumentosPorIdBeneficiario(@PathVariable Long id) {
        List<DocumentoDTO> listaDocumetos = documentoService.listarDocumentosPorIdBeneficiario(id);

        if(listaDocumetos.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaDocumetos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioDTO> atualizarBeneficiario(@PathVariable long id,  @RequestBody BeneficiarioDTO beneficiarioDTO) {
        BeneficiarioDTO atualizaBeneficiario = beneficiarioService.atualizarBeneficiario(id,beneficiarioDTO);
        return ResponseEntity.ok(atualizaBeneficiario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerBeneficiario(@PathVariable("id") Long id) {
       beneficiarioService.removerBeneficiario(id);
       return ResponseEntity.noContent().build();
    }

}
