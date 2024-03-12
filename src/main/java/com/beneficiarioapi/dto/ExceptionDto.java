package com.beneficiarioapi.dto;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ExceptionDto {

    private HttpStatus statusCode;
    private String mensagem;
    private Date time;
}
