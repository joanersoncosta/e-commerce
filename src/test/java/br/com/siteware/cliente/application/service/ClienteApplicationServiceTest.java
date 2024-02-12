package br.com.siteware.cliente.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.siteware.ClienteDataHelper;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
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
	void criaNovoCliente_comDadosValidos_retornaIdClienteResponse() {
		ClienteNovoRequest request = ClienteDataHelper.createClienteRequest();
	
		when(clienteRepository.salva(any())).thenReturn(new Cliente(request));
		ClienteIdResponse response = clienteApplicationService.criaNovoCliente(request);
	
		verify(clienteRepository, times(1)).salva(any());
		
		assertThat(response).isNotNull();
		assertThat(ClienteIdResponse.class).isEqualTo(response.getClass());
	}

}
