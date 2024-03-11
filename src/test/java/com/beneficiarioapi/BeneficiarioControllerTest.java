package com.beneficiarioapi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import com.beneficiarioapi.controller.BeneficiarioController;
import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.service.BeneficiarioService;
import com.beneficiarioapi.service.DocumentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;

import java.util.List;


public class BeneficiarioControllerTest {


    @Mock
    private BeneficiarioService beneficiarioService;

    @Mock
    private DocumentoService documentoService;

    @InjectMocks
    private BeneficiarioController beneficiarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void cadastrarBeneficiario_ValidInput_Success() {
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO();
        when(beneficiarioService.cadastrarBeneficiario(any(BeneficiarioDTO.class))).thenReturn(beneficiarioDTO);

        ResponseEntity<BeneficiarioDTO> response = beneficiarioController.cadastrarBeneficiario(beneficiarioDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(beneficiarioDTO, response.getBody());
    }


    @Test
    void listarBeneficiarios_BeneficiariosExist_ReturnsBeneficiarios() {
        List<BeneficiarioDTO> beneficiarios = Arrays.asList(new BeneficiarioDTO(), new BeneficiarioDTO());
        when(beneficiarioService.listarBeneficiarios()).thenReturn(beneficiarios);

        ResponseEntity<List<BeneficiarioDTO>> response = beneficiarioController.listarBeneficiarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(beneficiarios, response.getBody());
    }
}
