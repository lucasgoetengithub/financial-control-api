package com.br.lsolution.financialcontrol.api.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Integer status;
    private String message;

    public static ErrorResponse create(String message, HttpStatus httpStatus) {
        return ErrorResponse
                .builder()
                .status(httpStatus.value())
                .message(message)
                .build();
    }
}
