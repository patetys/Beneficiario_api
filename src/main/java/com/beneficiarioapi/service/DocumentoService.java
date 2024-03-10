package com.beneficiarioapi.service;

import com.beneficiarioapi.dto.DocumentoDTO;

import java.util.List;

public interface DocumentoService {

    List<DocumentoDTO> listarDocumentosPorIdBeneficiario(Long id);
    DocumentoDTO cadastrarDocumento(DocumentoDTO documentoDTO);
}
