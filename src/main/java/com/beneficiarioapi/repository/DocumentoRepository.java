package com.beneficiarioapi.repository;

import com.beneficiarioapi.Entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    public List<Documento> findByBeneficiarioId(long Id);
}
