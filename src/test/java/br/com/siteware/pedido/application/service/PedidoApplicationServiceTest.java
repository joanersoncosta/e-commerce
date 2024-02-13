package br.com.siteware.pedido.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.siteware.CarrinhoDataHelper;
import br.com.siteware.ClienteDataHelper;
import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.pedido.application.api.PedidoDetalhadoResponse;

@ExtendWith(MockitoExtension.class)
class PedidoApplicationServiceTest {

	@InjectMocks
	private PedidoApplicationService pedidoApplicationService;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private CarrinhoRepository carrinhoRepository;

	@Test
	@DisplayName("Busca Pedido")
	void buscaPedido_comClienteLogado_retornaDadosDoPedido() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCliente = cliente.getIdCliente();
		List<Carrinho> produtos = CarrinhoDataHelper.createListCarrinho();
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(carrinhoRepository.listaCarrinhoDoCliente(any())).thenReturn(produtos);

		PedidoDetalhadoResponse response = pedidoApplicationService.buscaPedido(email);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(carrinhoRepository, times(1)).listaCarrinhoDoCliente(idCliente);

		assertThat(produtos).isNotEmpty();
		assertEquals(3, produtos.size());
		assertThat(response).isNotNull();
		assertEquals(PedidoDetalhadoResponse.class, response.getClass());
	}

}
