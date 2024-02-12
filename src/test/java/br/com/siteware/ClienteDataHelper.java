package br.com.siteware;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.cliente.domain.enuns.Sexo;

public class ClienteDataHelper {
	public static final UUID ID_USUARIO_VALIDO = UUID.fromString("8d58875d-2455-4075-8b5d-57c73fcf1241");
	public static final String DATA_USUARIO_INVALIDO = "";

	public static Cliente createCliente() {
		return Cliente.builder().idCliente(ID_USUARIO_VALIDO).nome("Exemplo da Silva").email("exemplo@gmail.com")
				.sexo(Sexo.FEMININO).dataNascimento(LocalDate.parse("1997-05-12"))
				.momentoDoDacastro(LocalDateTime.now()).dataHoraDaultimaAlteracao(LocalDateTime.now()).build();
	}

	public static ClienteNovoRequest createClienteRequest() {
		return new ClienteNovoRequest("Exemplo da Silva", "exemplo@gmail.com", "1234567", "FEMININO", "1997-05-12");
	}
}
