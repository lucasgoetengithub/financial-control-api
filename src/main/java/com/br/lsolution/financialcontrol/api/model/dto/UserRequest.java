package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    String name;
    String email;
    String password;
    BigDecimal amount;
    Set<Perfil> perfis;
}
