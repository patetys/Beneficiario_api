package com.beneficiarioapi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private Date dataNascimento;

    private Date dataInclusao = new Date();

    private Date dataAtualizacao = new Date();

    @OneToMany(targetEntity = Documento.class,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "beneficiario_id", referencedColumnName = "id")
    private List<Documento> documentos = new ArrayList<Documento>();





}
