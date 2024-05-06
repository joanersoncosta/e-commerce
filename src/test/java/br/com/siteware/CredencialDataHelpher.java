package br.com.siteware;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.siteware.credencial.domain.AuthorityUsuario;
import br.com.siteware.credencial.domain.Credencial;
import br.com.siteware.credencial.domain.Perfil;

public class CredencialDataHelpher {
	
	public static Credencial createCredencialAdmin() {
		return Credencial.builder()
				.idCredencial(UUID.fromString("8f56de69-5c40-49ca-8b5a-dfced10c489a"))
				.usuario("admin@gmail.com")
				.senha(new BCryptPasswordEncoder().encode("admin123"))
				.perfil(new Perfil(AuthorityUsuario.ADMIN.name()))
				.validado(true)
				.build();
	}
	
	public static Credencial createCredencialCliente() {
		return Credencial.builder()
				.idCredencial(UUID.fromString("8d58875d-2455-4075-8b5d-57c73fcf1241"))
				.usuario("cliente1@gmail.com")
				.senha(new BCryptPasswordEncoder().encode("123456"))
				.perfil(new Perfil(AuthorityUsuario.CLIENTE.name()))
				.validado(true)
				.build();
	}
	
	public static Credencial createCredencialClienteTeste() {
		return Credencial.builder()
				.idCredencial(UUID.randomUUID())
				.usuario("cliente2@gmail.com")
				.senha(new BCryptPasswordEncoder().encode("123456"))
				.perfil(new Perfil(AuthorityUsuario.CLIENTE.name()))
				.validado(true)
				.build();
	}

}
