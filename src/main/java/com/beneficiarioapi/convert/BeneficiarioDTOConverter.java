package com.beneficiarioapi.convert;

import com.beneficiarioapi.Entity.Beneficiario;
import com.beneficiarioapi.dto.BeneficiarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BeneficiarioDTOConverter {

    private ModelMapper modelMapper;

    public BeneficiarioDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BeneficiarioDTO convertBeneficiariotoBeneficiarioDTO(Beneficiario beneficiario){

        BeneficiarioDTO beneficiarioDTO = modelMapper.map(beneficiario, BeneficiarioDTO.class);

        return  beneficiarioDTO;
    }

    public Beneficiario convertBeneficiarioDTOtoBeneficiario(BeneficiarioDTO beneficiarioDTO){

        Beneficiario beneficiario = modelMapper.map(beneficiarioDTO, Beneficiario.class);

        return  beneficiario;
    }
}
