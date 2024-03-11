package com.beneficiarioapi.service;

import com.beneficiarioapi.dto.DocumentoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentoService {

    List<DocumentoDTO> listarDocumentosPorIdBeneficiario(Long id);
}
