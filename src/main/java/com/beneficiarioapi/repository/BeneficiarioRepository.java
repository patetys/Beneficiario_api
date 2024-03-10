package com.beneficiarioapi.repository;

import com.beneficiarioapi.Entity.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
}