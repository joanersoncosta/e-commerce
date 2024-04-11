package br.com.siteware.cliente.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class ClienteListResponse {
	@Schema(description = "Este é o ID do Cliente")
	private UUID idCliente;
	@Schema(description = "Este é o nome do cliente", example = "Maria dos Santos")
	private String nome;
	@Schema(description = "Este é o E-mail do cliente", example = "maria@gmail.com")
	private String email;
	@Schema(description = "Este é o sexo do cliente", example = "FEMININO")
	private Sexo sexo;

	public ClienteListResponse(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.sexo = cliente.getSexo();
	}
	
	public static List<ClienteListResponse> converte(List<Cliente> clientes){
		return clientes.stream().map(ClienteListResponse::new).collect(Collectors.toList());
	}
}
