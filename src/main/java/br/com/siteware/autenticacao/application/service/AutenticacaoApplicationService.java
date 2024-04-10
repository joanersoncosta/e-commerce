package br.com.siteware.autenticacao.application.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.siteware.autenticacao.domain.Token;

public interface AutenticacaoApplicationService {
    Token autentica(UsernamePasswordAuthenticationToken userCredentials);
    Token reativaToken(String tokenExpirado);
}
