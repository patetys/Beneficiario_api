package com.beneficiarioapi.controller;


import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.service.DocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final ModelMapper modelMapper;

    public DocumentoController(DocumentoService documentoService, ModelMapper modelMapper) {
        this.documentoService = documentoService;
        this.modelMapper = modelMapper;
    }


    @PostMapping()
    public ResponseEntity<DocumentoDTO> cadastrarDocumento(@RequestBody DocumentoDTO documentoDTO) {
        DocumentoDTO novoDocumentoDTO = documentoService.cadastrarDocumento(documentoDTO);
        return new ResponseEntity<>(novoDocumentoDTO, HttpStatus.CREATED);
    }
}
