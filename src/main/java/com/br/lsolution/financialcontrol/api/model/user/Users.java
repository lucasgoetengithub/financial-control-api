package com.br.lsolution.financialcontrol.api.model.user;

import com.br.lsolution.financialcontrol.api.model.dto.RegisterDTO;
import com.br.lsolution.financialcontrol.api.model.dto.UserRequest;
import com.br.lsolution.financialcontrol.api.model.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String name;
    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    BigDecimal amount;


    public Set<Perfil> getPerfilEnum() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfilEnum(Perfil perfil) {
        perfis.add(perfil.getCode());
    }

    public static Users of(UserRequest request, String password) {
        return Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(password)
                .amount(request.getAmount())
                .build();
    }

    public static Users ofRegister(RegisterDTO request, String password) {
        return Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(password)
                .build();
    }

}