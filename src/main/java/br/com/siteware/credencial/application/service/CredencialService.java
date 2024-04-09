package br.com.siteware.credencial.application.service;

import br.com.siteware.credencial.domain.Credencial;
import br.com.siteware.credencial.domain.CredencialAdmin;
import br.com.siteware.credencial.domain.CredencialCliente;

public interface CredencialService {
	void criaNovaCredencial(CredencialCliente cliente);
	Credencial buscaCredencialPorUsuario(String usuario);
	void criaNovaCredencial(CredencialAdmin credencialAdmin);
}
