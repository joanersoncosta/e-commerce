package br.com.siteware.cliente.application.api;

import java.time.LocalDate;
import java.util.UUID;

import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.cliente.domain.enuns.Sexo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ClienteDetalhadoResponse {
	private UUID idCliente;
	private String nome;
	private String email;
	private String telefone;
	private Sexo sexo;
	private LocalDate dataNascimento;
	
	private ClienteDetalhadoResponse(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.sexo = cliente.getSexo();
		this.dataNascimento = cliente.getDataNascimento();
	}
	
	public static ClienteDetalhadoResponse converteClienteParaResponse(Cliente cliente) {
		return new ClienteDetalhadoResponse(cliente);
	}
}
