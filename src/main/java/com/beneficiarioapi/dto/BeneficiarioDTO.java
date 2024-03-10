package com.beneficiarioapi.dto;



import com.beneficiarioapi.Entity.Documento;
import lombok.*;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BeneficiarioDTO {

    private Long id;
    private String nome;
    private String telefone;
    private Date dataNascimento;
}
