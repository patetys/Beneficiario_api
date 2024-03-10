package com.beneficiarioapi.service;

import com.beneficiarioapi.Entity.Beneficiario;
import com.beneficiarioapi.Entity.Documento;
import com.beneficiarioapi.convert.BeneficiarioDTOConverter;
import com.beneficiarioapi.convert.DocumentoDTOConverter;
import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.repository.BeneficiarioRepository;
import com.beneficiarioapi.repository.DocumentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final BeneficiarioRepository beneficiarioRepository;
    private final DocumentoDTOConverter documentoDTOConverter;
    private final BeneficiarioDTOConverter beneficiarioDTOConverter;
    private final ModelMapper modelMapper;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, BeneficiarioRepository beneficiarioRepository, DocumentoDTOConverter documentoDTOConverter, BeneficiarioDTOConverter beneficiarioDTOConverter, ModelMapper modelMapper) {
        this.documentoRepository = documentoRepository;
        this.beneficiarioRepository = beneficiarioRepository;
        this.documentoDTOConverter = documentoDTOConverter;
        this.beneficiarioDTOConverter = beneficiarioDTOConverter;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DocumentoDTO> listarDocumentosPorIdBeneficiario(Long id) {

        List<Documento> listaDocumentos = documentoRepository.findByBeneficiarioId(id);

        return listaDocumentos.stream()
                .map(documento -> modelMapper.map(documento, DocumentoDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public DocumentoDTO cadastrarDocumento(Long id, DocumentoDTO documentoDTO) {

        Optional<Beneficiario> optionalBeneficiario = beneficiarioRepository.findById(id);

        if (optionalBeneficiario.isPresent()) {
            BeneficiarioDTO beneficiarioDTO = beneficiarioDTOConverter.convertBeneficiariotoBeneficiarioDTO(optionalBeneficiario.get());
            documentoDTO.setBeneficiarioDTO(beneficiarioDTO);
            Documento documento = documentoRepository.save(documentoDTOConverter.convertDocumentoDTOtoDocumento(documentoDTO));
            return documentoDTOConverter.convertDocumentotoDocumentoDTO(documento);
        } else {
            throw new RuntimeException("Beneficiario não encontrando com id: " + id);
        }
    }
}
