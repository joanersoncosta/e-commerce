package br.com.siteware.cliente.application.api;

import java.time.LocalDate;
import java.util.UUID;

import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.cliente.domain.enuns.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ClienteDetalhadoResponse {
	@Schema(description = "Este é o ID do Cliente")
	private UUID idCliente;
	@Schema(description = "Este é o nome do cliente", example = "Maria dos Santos")
	private String nome;
	@Schema(description = "Este é o E-mail do cliente", example = "maria@gmail.com")
	private String email;
	@Schema(description = "Este é o sexo do cliente", example = "FEMININO")
	private Sexo sexo;
	@Schema(description = "Esta é a data de nascimento do cliente", example = "1996-12-05")
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
