package com.br.lsolution.financialcontrol.api.model.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_RECOVERY")
public class UserRecoveryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userEmail;

    private String codigoRecuperacao;

    private LocalDateTime dataCriacao;

    private Boolean usado;

    private Boolean expirado;
}
