package com.br.lsolution.financialcontrol.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryDTO {

    private String email;
    private String codigo;
    private String password;
    private String confirmPassword;

}
