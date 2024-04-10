package br.com.siteware.config.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.siteware.credencial.domain.Credencial;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {
    @Value("${e-commerce.jwt.expiracao}")
    private String expiracao;
    @Value("${e-commerce.jwt.chave}")
    private String chave;
    
    public String gerarToken(org.springframework.security.core.Authentication authentication) {
        return gerarToken((Credencial) authentication.getPrincipal());
    }

    public String gerarToken(Credencial credencial) {
        log.info("[inicio] TokenService - criação de token");
        String token = Jwts.builder()
                .setIssuer("API do Produdoro")
                .setSubject(credencial.getUsuario())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now()
                        .plusMinutes(Long.valueOf(expiracao))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(SignatureAlgorithm.HS512, chave)//
                .compact();
        log.info("[finaliza] TokenService - criação de token");
		return token;
    }

    public Optional<String> getUsuario(String token) {
        try {
            log.info("[inicio] TokenService - extração do ID do Token");
            var claims = Jwts.parser().setSigningKey(chave).parseClaimsJws(token).getBody();
            log.info("[finaliza] TokenService - extração do ID do Token");
            return Optional.of(claims.getSubject());
        } catch (SignatureException ex) {
            return Optional.empty();
        } catch (ExpiredJwtException ex) {
            var claims = ex.getClaims();
            log.info("[finaliza] TokenService - extração do ID do Token");
            return Optional.of(claims.getSubject());
        }
    }

    public Optional<String> getUsuarioByBearerToken(String bearerToken) {
        log.info("[inicio] TokenService - getUsuarioByBearerToken");
        String token = bearerToken.substring(7, bearerToken.length());
        log.info(token);
        log.info("[finaliza] TokenService - getUsuarioByBearerToken");
        return this.getUsuario(token);
    }

}
