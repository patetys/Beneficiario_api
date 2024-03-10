package com.beneficiarioapi.dto;



import lombok.*;
import java.util.Date;


@Data
@Getter
@Setter
public class BeneficiarioDTO {

    public BeneficiarioDTO(Long id, String nome, String telefone, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    private Long id;
    private String nome;
    private String telefone;
    private Date dataNascimento;

}
