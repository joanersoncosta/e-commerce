package br.com.siteware.carrinho.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import br.com.siteware.ProdutoDataHelper;
import br.com.siteware.carrinho.application.api.CarrinhoIdResponse;
import br.com.siteware.carrinho.application.api.CarrinhoRequest;
import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;

@ExtendWith(MockitoExtension.class)
class CarrinhoApplicationServiceTest {

	@InjectMocks
	private CarrinhoApplicationService carrinhoApplicationService;
	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private CarrinhoRepository carrinhoRepository;

	@Test
	@DisplayName("Adiciona Produto Ao Carrinho")
	void adicionaProdutoAoCarrinho() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		Produto produto = ProdutoDataHelper.createProduto();
		Produto produtoMock = mock(Produto.class);
		CarrinhoRequest request = CarrinhoDataHelper.carrinhoRequest();
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produtoMock));
		when(carrinhoRepository.salva(any())).thenReturn(new Carrinho(cliente.getIdCliente(), produto, request.getQuantidade()));

		CarrinhoIdResponse response = carrinhoApplicationService.adicionaProdutoAoCarrinho(email, request);

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(request.getIdProduto());
		verify(produtoMock, times(1)).validaEstoque(request.getQuantidade());
		verify(produtoMock, times(1)).incrementaProdutosVendidos(request.getQuantidade());
		verify(produtoMock, times(1)).validaStatusEstoque();
		verify(produtoRepository, times(1)).salva(produtoMock);
		verify(carrinhoRepository, times(1)).salva(any());

		assertThat(response).isNotNull();
		assertEquals(CarrinhoIdResponse.class, response.getClass());
	}

	@Test
	void listaCarrinhoDoCliente() {
	}

	@Test
	void detalhaCarrinho() {
	}

	@Test
	void removeCarrinho() {
	}

	@Test
	void editaCarrinho() {
	}

}