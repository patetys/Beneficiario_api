package com.beneficiarioapi.service;

import com.beneficiarioapi.dto.BeneficiarioDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface BeneficiarioService {
    BeneficiarioDTO cadastrarBeneficiario(BeneficiarioDTO beneficiarioDTO);
    List<BeneficiarioDTO> listarBeneficiarios();
    BeneficiarioDTO atualizarBeneficiario(Long Id, BeneficiarioDTO beneficiarioDTO);
    void removerBeneficiario(Long id);
}
