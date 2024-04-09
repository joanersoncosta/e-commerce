package br.com.siteware.credencial.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.siteware.admin.application.api.AdminRequest;
import lombok.Getter;

@Getter
public class CredencialAdmin {
	private String email;
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;
	@NotNull
	private Perfil perfil;
	
	public CredencialAdmin(AdminRequest admin) {
		this.email = admin.getEmail();
		this.senha = admin.getSenha();
		this.perfil = new Perfil(AuthorityUsuario.ADMIN.name());
	}
	
}
