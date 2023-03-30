package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    Integer id;
    String name;
    String email;
    BigDecimal amount;

    public static UserResponse of(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .amount(users.getAmount())
                .build();
    }
}
