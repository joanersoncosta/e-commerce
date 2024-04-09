package br.com.siteware.config.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.siteware.credencial.application.repository.CredencialRepository;
import br.com.siteware.credencial.domain.Perfil;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoSecurityService implements UserDetailsService {
    private final CredencialRepository credencialRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        log.info("[inicio] AutenticacaoSecurityService - buscando credencial pelo usuario");
        var credencial = credencialRepository.buscaCredencialPorUsuario(usuario);
        List<String> roles = new ArrayList<>();
        Set<Perfil> authorities = credencial.getAuthoritie();
        for (Perfil authority : authorities) {roles.add(authority.getNome());}
        UserDetails userDetails;
        userDetails = User.builder()
                .username(credencial.getUsername())
                .password(credencial.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
        log.info("[finaliza] AutenticacaoSecurityService - buscando credencial pelo usuario");
        return Optional.ofNullable(credencial).orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Não existe credencial para o cliente informado!"));
    }
}
