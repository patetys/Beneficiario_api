package com.beneficiarioapi.controller;

import com.beneficiarioapi.dto.BeneficiarioDTO;
import com.beneficiarioapi.dto.DocumentoDTO;
import com.beneficiarioapi.service.BeneficiarioService;
import com.beneficiarioapi.service.DocumentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/beneficiarios",produces = {"application/json"})
@Tag(name = "open-api")
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;
    private final DocumentoService documentoService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    public BeneficiarioController(BeneficiarioService beneficiarioService, DocumentoService documentoService) {
        this.beneficiarioService = beneficiarioService;
        this.documentoService = documentoService;
    }


    @PostMapping()
    public ResponseEntity<BeneficiarioDTO> cadastrarBeneficiario(@RequestBody @Valid BeneficiarioDTO BeneficiarioDto) {
        BeneficiarioDTO beneficiarioDTO = beneficiarioService.cadastrarBeneficiario(BeneficiarioDto);
        return new ResponseEntity<>(beneficiarioDTO,HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<BeneficiarioDTO>> listarBeneficiarios() {
        List<BeneficiarioDTO> listaBeneficiarios = beneficiarioService.listarBeneficiarios();

        if(listaBeneficiarios.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(listaBeneficiarios);
    }


    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<DocumentoDTO>> listarDocumentosPorIdBeneficiario(@PathVariable Long id) {


        List<DocumentoDTO> listaDocumetos = documentoService.listarDocumentosPorIdBeneficiario(id);

        if(listaDocumetos.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaDocumetos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioDTO> atualizarBeneficiario(@PathVariable long id,  @RequestBody @Valid BeneficiarioDTO beneficiarioDTO) {
        BeneficiarioDTO atualizaBeneficiario = beneficiarioService.atualizarBeneficiario(id,beneficiarioDTO);
        return ResponseEntity.ok(atualizaBeneficiario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerBeneficiario(@PathVariable("id") Long id) {
       beneficiarioService.removerBeneficiario(id);
       return ResponseEntity.noContent().build();
    }

}
