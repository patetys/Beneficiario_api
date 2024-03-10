package com.beneficiarioapi.repository;

import com.beneficiarioapi.Entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    public List<Documento> findByBeneficiarioId(long Id);
}
