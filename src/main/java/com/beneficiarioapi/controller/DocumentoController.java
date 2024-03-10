package com.beneficiarioapi.controller;

import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.service.DocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final ModelMapper modelMapper;

    public DocumentoController(DocumentoService documentoService, ModelMapper modelMapper) {
        this.documentoService = documentoService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/beneficiario/{id}")
    public ResponseEntity<DocumentoDTO> cadastrarDocumento(@PathVariable Long id , @RequestBody DocumentoDTO documentoDTO) {
        DocumentoDTO novoDocumentoDTO = documentoService.cadastrarDocumento(id,documentoDTO);
        return new ResponseEntity<>(novoDocumentoDTO, HttpStatus.CREATED);
    }
}
