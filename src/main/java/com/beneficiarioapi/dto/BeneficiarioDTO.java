package com.beneficiarioapi.dto;




import com.beneficiarioapi.Entity.Documento;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BeneficiarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotNull(message = "Telefone é obrigatório")
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
    @NotNull(message = "Data de nascimento é obrigatório")
    @NotBlank(message = "Data de nascimento é obrigatório")
    private Date dataNascimento;

    private List<Documento> documentos = new ArrayList<>();;
}
