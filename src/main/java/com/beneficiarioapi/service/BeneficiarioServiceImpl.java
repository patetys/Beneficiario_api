package com.beneficiarioapi.service;

import com.beneficiarioapi.Entity.Beneficiario;
import com.beneficiarioapi.Entity.Documento;
import com.beneficiarioapi.convert.BeneficiarioDTOConverter;
import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.repository.BeneficiarioRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BeneficiarioServiceImpl implements BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final BeneficiarioDTOConverter beneficiarioDTOConverter;
    private final ModelMapper modelMapper;

    public BeneficiarioServiceImpl(BeneficiarioRepository beneficiarioRepository, ModelMapper modelMapper, BeneficiarioDTOConverter beneficiarioDTOConverter) {
        this.beneficiarioRepository = beneficiarioRepository;
        this.modelMapper = modelMapper;
        this.beneficiarioDTOConverter = beneficiarioDTOConverter;
    }

    @Override
    public BeneficiarioDTO cadastrarBeneficiario(BeneficiarioDTO beneficiarioDTO) {

        List<Documento> listDocuments = beneficiarioDTO.getDocumentos().stream()
                                             .collect(Collectors.toList());

        beneficiarioDTO.setDocumentos(listDocuments);

        Beneficiario beneficiario = beneficiarioRepository.save(beneficiarioDTOConverter.convertBeneficiarioDTOtoBeneficiario(beneficiarioDTO));
        return beneficiarioDTOConverter.convertBeneficiariotoBeneficiarioDTO(beneficiario);
    }

    @Override
    public List<BeneficiarioDTO> listarBeneficiarios() {
        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();

        return beneficiarios.stream()
                .map(beneficiario -> modelMapper.map(beneficiario, BeneficiarioDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public BeneficiarioDTO atualizarBeneficiario(Long Id, BeneficiarioDTO beneficiarioDTO) {

        Beneficiario updateBeneficiario = beneficiarioRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiario não existe com o id: " + Id));

        updateBeneficiario.setNome(beneficiarioDTO.getNome());
        updateBeneficiario.setTelefone(beneficiarioDTO.getTelefone());
        updateBeneficiario.setDataNascimento(beneficiarioDTO.getDataNascimento());

        beneficiarioRepository.save(updateBeneficiario);

        return beneficiarioDTOConverter.convertBeneficiariotoBeneficiarioDTO(updateBeneficiario);
    }

    @Override
    public void removerBeneficiario(Long id) {
        beneficiarioRepository.deleteById(id);
    }
}
