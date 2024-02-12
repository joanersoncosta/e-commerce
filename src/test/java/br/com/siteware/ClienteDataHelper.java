package br.com.siteware;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
	
	public static List<Cliente> createListCliente() {
		return List.of(
				Cliente.builder().idCliente(UUID.randomUUID()).nome("Exemplo da Silva").email("exemplo@gmail.com")
				.sexo(Sexo.FEMININO).dataNascimento(LocalDate.parse("1997-05-12"))
				.momentoDoDacastro(LocalDateTime.now()).dataHoraDaultimaAlteracao(LocalDateTime.now()).build(),
				Cliente.builder().idCliente(UUID.randomUUID()).nome("Exemplo 2").email("exemplo2@gmail.com")
				.sexo(Sexo.MASCULINO).dataNascimento(LocalDate.parse("1995-01-18"))
				.momentoDoDacastro(LocalDateTime.now()).dataHoraDaultimaAlteracao(LocalDateTime.now()).build(),
				Cliente.builder().idCliente(UUID.randomUUID()).nome("Exemplo 3").email("exemplo3@gmail.com")
				.sexo(Sexo.FEMININO).dataNascimento(LocalDate.parse("1991-08-22"))
				.momentoDoDacastro(LocalDateTime.now()).dataHoraDaultimaAlteracao(LocalDateTime.now()).build(),
				Cliente.builder().idCliente(UUID.randomUUID()).nome("Exemplo 3").email("exemplo4@gmail.com")
				.sexo(Sexo.MASCULINO).dataNascimento(LocalDate.parse("1989-03-25"))
				.momentoDoDacastro(LocalDateTime.now()).dataHoraDaultimaAlteracao(LocalDateTime.now()).build()
				);
	}
}
