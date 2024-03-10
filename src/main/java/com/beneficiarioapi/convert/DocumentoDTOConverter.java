package com.beneficiarioapi.convert;

import com.beneficiarioapi.Entity.Beneficiario;
import com.beneficiarioapi.Entity.Documento;
import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DocumentoDTOConverter {

    private ModelMapper modelMapper;

    public DocumentoDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DocumentoDTO convertDocumentotoDocumentoDTO(Documento documento){

        DocumentoDTO documentoDTO = modelMapper.map(documento, DocumentoDTO.class);

        return  documentoDTO;
    }

    public Documento convertDocumentoDTOtoDocumento(DocumentoDTO documentoDTO){

        Documento documento = modelMapper.map(documentoDTO, Documento.class);

        return  documento;
    }
}
