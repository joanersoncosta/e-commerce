package br.com.siteware.credencial.application.service;

import org.springframework.stereotype.Service;

import br.com.siteware.credencial.application.repository.CredencialRepository;
import br.com.siteware.credencial.domain.Credencial;
import br.com.siteware.credencial.domain.CredencialAdmin;
import br.com.siteware.credencial.domain.CredencialCliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrendencialApplicationService implements CredencialService {
	private final CredencialRepository credencialRepository;
	
	@Override
	public void criaNovaCredencial(CredencialCliente clienteNovo) {
		log.info("[inicia] CrendencialService - criaNovaCredencial");
		var novaCredencial = new Credencial(clienteNovo.getEmail(), clienteNovo.getSenha(), clienteNovo.getPerfil());
		credencialRepository.salva(novaCredencial);
		log.info("[finaliza] CrendencialService - criaNovaCredencial");
	}
	
	@Override
	public void criaNovaCredencial(CredencialAdmin credencialAdmin) {
		log.info("[inicia] CrendencialService - criaNovaCredencial");
		var novaCredencial = new Credencial(credencialAdmin.getEmail(), credencialAdmin.getSenha(), credencialAdmin.getPerfil());
		credencialRepository.salva(novaCredencial);
	}
	
	@Override
	public Credencial buscaCredencialPorUsuario(String usuario) {
		log.info("[inicia] CredencialSpringDataJpaService - buscaCredencialPorUsuario");
		Credencial credencial = credencialRepository.buscaCredencialPorUsuario(usuario);
		log.info("[finaliza] CredencialSpringDataJpaService - buscaCredencialPorUsuario");
		return credencial;
	}

}
