package br.com.siteware.pedido.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
		when(clienteRepository.detalhaClientePorId(any())).thenReturn(Optional.of(cliente));
		when(carrinhoRepository.listaCarrinhoDoCliente(any())).thenReturn(produtos);

		PedidoDetalhadoResponse response = pedidoApplicationService.buscaPedido(email, cliente.getIdCliente());
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(clienteRepository, times(1)).detalhaClientePorId(any());
		verify(carrinhoRepository, times(1)).listaCarrinhoDoCliente(idCliente);

		assertThat(produtos).isNotEmpty();
		assertEquals(3, produtos.size());
		assertThat(response).isNotNull();
		assertEquals(PedidoDetalhadoResponse.class, response.getClass());
	}
	
	@Test
	@DisplayName("Retorna Pedido sem Produtos associados")
	void buscaPedido_comClienteLogado_retornaListaProdutosVazio() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCliente = cliente.getIdCliente();
		List<Carrinho> produtos = CarrinhoDataHelper.createListCarrinhoVazio();
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(clienteRepository.detalhaClientePorId(any())).thenReturn(Optional.of(cliente));
		when(carrinhoRepository.listaCarrinhoDoCliente(any())).thenReturn(Collections.emptyList());

		PedidoDetalhadoResponse response = pedidoApplicationService.buscaPedido(email, cliente.getIdCliente());
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(clienteRepository, times(1)).detalhaClientePorId(any());
		verify(carrinhoRepository, times(1)).listaCarrinhoDoCliente(idCliente);

		assertThat(produtos).isEmpty();
		assertThat(response).isNotNull();
		assertEquals(PedidoDetalhadoResponse.class, response.getClass());
	}

}
