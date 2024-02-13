package br.com.siteware.carrinho.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.springframework.http.HttpStatus;

import br.com.siteware.CarrinhoDataHelper;
import br.com.siteware.ClienteDataHelper;
import br.com.siteware.ProdutoDataHelper;
import br.com.siteware.carrinho.application.api.CarrinhoIdResponse;
import br.com.siteware.carrinho.application.api.CarrinhoListResponse;
import br.com.siteware.carrinho.application.api.CarrinhoRequest;
import br.com.siteware.carrinho.application.api.EditaCarrinhoRequest;
import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
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
	@DisplayName("Adiciona Produto com IdProduto Invalido, retorna erro")
	void adicionaProdutoAoCarrinho_comIdProdutoInvalido_retornaErro() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		CarrinhoRequest request = CarrinhoDataHelper.carrinhoRequestInvalido();
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> carrinhoApplicationService.adicionaProdutoAoCarrinho(email, request));

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(request.getIdProduto());

		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}

	@Test
	@DisplayName("Lista Produto do Carrinho")
	void listaCarrinhoDoCliente_comUsuarioLogado_retornaListaProdutosNoCarrinho() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCliente = cliente.getIdCliente();
		List<Carrinho> produtos = CarrinhoDataHelper.createListCarrinho();
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(carrinhoRepository.listaCarrinhoDoCliente(any())).thenReturn(produtos);

		List<CarrinhoListResponse> response = carrinhoApplicationService.listaCarrinhoDoCliente(email);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(carrinhoRepository, times(1)).listaCarrinhoDoCliente(idCliente);

		assertThat(response).isNotEmpty();
		assertEquals(3, response.size());
		assertEquals(produtos.get(0).getIdProduto(), response.get(0).getIdProduto());
	}
	
	@Test
	@DisplayName("Retorna Lista vazia de Produtos")
	void listaCarrinhoDoCliente_comUsuarioLogado_retornaListaVazia() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCliente = cliente.getIdCliente();
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(carrinhoRepository.listaCarrinhoDoCliente(any())).thenReturn(Collections.emptyList());

		List<CarrinhoListResponse> response = carrinhoApplicationService.listaCarrinhoDoCliente(email);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(carrinhoRepository, times(1)).listaCarrinhoDoCliente(idCliente);

		assertThat(response).isEmpty();
	}

	@Test
	@DisplayName("Detalha Carrinho")
	void detalhaCarrinho_comIdCarrinhoValido_retornaCarrinho() {
		Carrinho carrinho = CarrinhoDataHelper.createCarrinho();
		UUID idCarrinho = carrinho.getIdCarrinho();
		when(carrinhoRepository.buscaCarrinhoPorId(any())).thenReturn(Optional.of(carrinho));

		Carrinho response = carrinhoApplicationService.detalhaCarrinho(idCarrinho);
		
		verify(carrinhoRepository, times(1)).buscaCarrinhoPorId(idCarrinho);

		assertThat(response).isNotNull();
		assertEquals(Carrinho.class, response.getClass());
	}
	
	@Test
	@DisplayName("Detalha Carrinho com idCarrinho invalido")
	void detalhaCarrinho_comIdCarrinhoInvalido_retornaErro() {
	    UUID idCarrinho = UUID.randomUUID();
		when(carrinhoRepository.buscaCarrinhoPorId(any())).thenReturn(Optional.empty());

	    APIException ex = assertThrows(APIException.class, 
	            () -> carrinhoApplicationService.detalhaCarrinho(idCarrinho));
	    
	    verify(carrinhoRepository, times(1)).buscaCarrinhoPorId(idCarrinho);

	    assertEquals("Carrinho não encontrado.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}

	@Test
	@DisplayName("Remove Produto do Carrinho")
	void removeCarrinho_comIdValido_removeProdutoDoCarrinho() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		Produto produtoMock = mock(Produto.class);
		UUID idProduto = produtoMock.getIdProduto();
		Carrinho carrinhoMock = mock(Carrinho.class);
		UUID idCarrinho = CarrinhoDataHelper.createCarrinho().getIdCarrinho();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produtoMock));
		when(carrinhoRepository.buscaCarrinhoPorId(any())).thenReturn(Optional.of(carrinhoMock));
		doNothing().when(carrinhoRepository).atualizaProdutosVendidos(carrinhoMock, produtoMock);
		doNothing().when(carrinhoRepository).removeCarrinho(carrinhoMock);
		
		carrinhoApplicationService.removeCarrinho(email, idCarrinho);;

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(carrinhoRepository, times(1)).buscaCarrinhoPorId(idCarrinho);
		verify(carrinhoMock, times(1)).pertenceCliente(cliente);
		verify(carrinhoRepository, times(1)).atualizaProdutosVendidos(carrinhoMock, produtoMock);
		verify(carrinhoRepository, times(1)).removeCarrinho(any());
	}
	
	@Test
	@DisplayName("Remove Produto com idCarrinho Invalido")
	void removeCarrinho_comIdCarrinhoInvalido_retornaErro() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		UUID idCarrinho = UUID.randomUUID();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(carrinhoRepository.buscaCarrinhoPorId(any())).thenReturn(Optional.empty());
		
		APIException ex = assertThrows(APIException.class, 
				() -> carrinhoApplicationService.removeCarrinho(email, idCarrinho));
		
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(carrinhoRepository, times(1)).buscaCarrinhoPorId(idCarrinho);
	
	    assertEquals("Carrinho não encontrado.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}

	@Test
	void editaCarrinho() {
		Cliente cliente = ClienteDataHelper.createCliente();
		String email = cliente.getEmail();
		Carrinho carrinhoMock = mock(Carrinho.class);
		UUID idCarrinho = CarrinhoDataHelper.createCarrinho().getIdCarrinho();
		EditaCarrinhoRequest request = CarrinhoDataHelper.editaCarrinhoRequest();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(carrinhoRepository.buscaCarrinhoPorId(any())).thenReturn(Optional.of(carrinhoMock));
		doNothing().when(carrinhoRepository).atualizaCarrinho(carrinhoMock);
		
		carrinhoApplicationService.editaCarrinho(email, idCarrinho, request);

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(carrinhoRepository, times(1)).buscaCarrinhoPorId(idCarrinho);
		verify(carrinhoMock, times(1)).pertenceCliente(cliente);
		verify(carrinhoMock, times(1)).atualizaCarrinho(request);
		verify(carrinhoRepository, times(1)).atualizaCarrinho(carrinhoMock);
	}

}
