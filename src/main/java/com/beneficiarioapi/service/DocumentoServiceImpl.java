package com.beneficiarioapi.service;

import com.beneficiarioapi.Entity.Documento;
import com.beneficiarioapi.convert.DocumentoDTOConverter;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.repository.DocumentoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoDTOConverter documentoDTOConverter;
    private final ModelMapper modelMapper;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository,  DocumentoDTOConverter documentoDTOConverter, ModelMapper modelMapper) {
        this.documentoRepository = documentoRepository;
        this.documentoDTOConverter = documentoDTOConverter;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DocumentoDTO> listarDocumentosPorIdBeneficiario(Long id) {

        List<Documento> listaDocumentos = documentoRepository.findByBeneficiarioId(id);

        if(listaDocumentos.isEmpty())
        {
            throw  new ResourceNotFoundException("Não existe documento para o beneficiario com o id: " + id);
        }

        return listaDocumentos.stream()
                .map(documento -> modelMapper.map(documento, DocumentoDTO.class))
                .collect(Collectors.toList());

    }


}