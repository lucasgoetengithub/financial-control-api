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
public class SucessReponse {

    private Integer status;
    private String message;

    public static SucessReponse create(String message) {
        return SucessReponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}
