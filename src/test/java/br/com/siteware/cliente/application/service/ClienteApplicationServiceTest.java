package br.com.siteware.cliente.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.siteware.ClienteDataHelper;
import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteListResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;

@ExtendWith(MockitoExtension.class)
class ClienteApplicationServiceTest{
	
	@InjectMocks
	private ClienteApplicationService clienteApplicationService;
	@Mock
	private ClienteRepository clienteRepository;

	@Test
	@DisplayName("Salva Cliente - com dados validos - retorna ClienteIdResponse")
	void criaNovoCliente_comDadosValidos_retornaClienteIdResponse() {
		ClienteNovoRequest request = ClienteDataHelper.createClienteRequest();
	
		when(clienteRepository.salva(any())).thenReturn(new Cliente(request));
		ClienteIdResponse response = clienteApplicationService.criaNovoCliente(request);
	
		verify(clienteRepository, times(1)).salva(any());
		
		assertNotNull(response);
		assertEquals(ClienteIdResponse.class, response.getClass());
	}

	@Test
	@DisplayName("Busca Cliente por id")
	void buscaClientePorId_comIdClienteValido_retornaClienteDetalhadoResponse() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCliente = cliente.getIdCliente();
		
		when(clienteRepository.detalhaClientePorId(any())).thenReturn(Optional.of(cliente));
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);

		ClienteDetalhadoResponse response = clienteApplicationService.buscaClientePorId(email, idCliente);

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(clienteRepository, times(1)).detalhaClientePorId(idCliente);
		
		assertNotNull(response);
		assertEquals(response.getIdCliente(), cliente.getIdCliente());
		assertEquals(ClienteDetalhadoResponse.class, response.getClass());
	}
	
	@Test
	@DisplayName("Busca Cliente por Email")
	void buscaClientePorEmail_comEmailValido_retornaCliente() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);

		Cliente response = clienteApplicationService.detalhaClientePorEmail(email);

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		
		assertNotNull(response);
		assertEquals(response.getIdCliente(), cliente.getIdCliente());
		assertEquals(Cliente.class, response.getClass());
	}
	
	@Test
	@DisplayName("Busca Todos os Clientes")
	void listaClientes_comEmailAdminValido_retornaListaDeClientes() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		List<Cliente> listCliente = ClienteDataHelper.createListCliente();
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(clienteRepository.buscaClientes()).thenReturn(listCliente);

		List<ClienteListResponse> response = clienteApplicationService.buscaTodosOsClientes(email);

		verify(clienteRepository, times(1)).buscaClientes();
		
		assertThat(response).isNotEmpty();
		assertEquals(4, response.size());
	}
}
