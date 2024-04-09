package br.com.siteware.credencial.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import lombok.Getter;

@Getter
public class CredencialCliente {
	private String email;
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;
	@NotNull
	private Perfil nome;
	
	public CredencialCliente(ClienteNovoRequest cliente) {
		this.email = cliente.getEmail();
		this.senha = cliente.getSenha();
		this.nome = new Perfil("CLIENTE");
	}
}
