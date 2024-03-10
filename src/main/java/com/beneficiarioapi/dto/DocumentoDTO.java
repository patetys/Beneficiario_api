package com.beneficiarioapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private String tipoDocumento;
    private String descricao;
    @JsonIgnore
    private BeneficiarioDTO beneficiarioDTO;
}
