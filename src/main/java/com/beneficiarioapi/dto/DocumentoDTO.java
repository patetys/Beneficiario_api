package com.beneficiarioapi.dto;

import lombok.*;


@Getter
@Setter
@Data
public class DocumentoDTO {

    public DocumentoDTO(String tipoDocumento, String descricao) {
        this.tipoDocumento = tipoDocumento;
        this.descricao = descricao;
    }

    private Long id;
    private String tipoDocumento;
    private String descricao;

}
