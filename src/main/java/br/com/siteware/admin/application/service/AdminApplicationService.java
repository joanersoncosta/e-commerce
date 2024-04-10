package br.com.siteware.admin.application.service;

import org.springframework.stereotype.Service;

import br.com.siteware.admin.application.api.AdminRequest;
import br.com.siteware.credencial.application.service.CredencialService;
import br.com.siteware.credencial.domain.CredencialAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminApplicationService implements AdminService {
	private final CredencialService credencialService;

	@Override
	public void criaCredencialAdmin(AdminRequest adminRequest) {
		log.info("[inicia] AdminApplicationService - criaCredencialAdmin");
		credencialService.criaNovaCredencial(new CredencialAdmin(adminRequest));
		log.info("[finaliza] AdminApplicationService - criaCredencialAdmin");
	}

}
