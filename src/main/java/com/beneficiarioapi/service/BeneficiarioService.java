package com.beneficiarioapi.service;

import com.beneficiarioapi.dto.BeneficiarioDTO;


import java.util.List;

public interface BeneficiarioService {
    BeneficiarioDTO cadastrarBeneficiario(BeneficiarioDTO beneficiarioDTO);
    List<BeneficiarioDTO> listarBeneficiarios();
    BeneficiarioDTO atualizarBeneficiario(Long Id, BeneficiarioDTO beneficiarioDTO);
    void removerBeneficiario(Long id);
}
