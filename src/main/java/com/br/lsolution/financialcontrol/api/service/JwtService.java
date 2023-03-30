package com.br.lsolution.financialcontrol.api.service;

import com.br.lsolution.financialcontrol.api.model.user.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {

    String gerarToken(Users usuario);

    Claims obterClaims(String token) throws ExpiredJwtException;

    boolean isTokenValido(String token);

    String obterLoginUsuario( String token );
}
