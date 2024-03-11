package com.beneficiarioapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DocumentoDTO {

    @Valid

    private Long id;
    @NotNull(message = "Tipo de documento é obrigatório")
    @NotBlank(message = "Tipo de documento é obrigatório")
    private String tipoDocumento;
    @NotNull(message = "Descrição é obrigatório")
    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;
    @JsonIgnore
    private BeneficiarioDTO beneficiarioDTO;
}
