package br.com.siteware.produto.application.service;

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

import br.com.siteware.ClienteDataHelper;
import br.com.siteware.ProdutoDataHelper;
import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.AlteraPromocaoProdutoRequest;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoListResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.api.PromocaoProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;

@ExtendWith(MockitoExtension.class)
class ProdutoApplicationServiceTest {

	@InjectMocks
	private ProdutoApplicationService produtoApplicationService;
	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private ClienteRepository clienteRepository;

	@Test
	@DisplayName("Cadastra Produto com sucesso")
	void cadastraProduto_comDadosValidos_retornaProdutoIdResponse() {
		Cliente cliente = ClienteDataHelper.createCliente();
		ProdutoRequest request = ProdutoDataHelper.createProdutorequest();
		String email = cliente.getEmail();
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.salva(any())).thenReturn(new Produto(request));
		
		ProdutoIdResponse response = produtoApplicationService.cadastraProduto(email, request);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).salva(any());

		assertThat(response).isNotNull();
		assertEquals(ProdutoIdResponse.class, response.getClass());
	}
	
	@Test
	@DisplayName("Busca Produto Por Id")
	void buscaProduto_comIdValido_retornaProdutoDetalhadoResponse() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		
		ProdutoDetalhadoResponse response = produtoApplicationService.buscaProdutoPorId(idProduto);
	
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);

		assertThat(response).isNotNull();
		assertEquals(ProdutoDetalhadoResponse.class, response.getClass());
	}
	
	@Test
	@DisplayName("Busca Produto Por Id - retorna erro")
	void buscaProduto_comIdInvalido_retornaErro() {
		UUID idProduto = UUID.randomUUID();
		
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());
		
		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.buscaProdutoPorId(idProduto));

		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);

		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}

	@Test
	@DisplayName("Busca Todos os Produto")
	void listaProduto_retornaListaDeProdutos() {
		List<Produto> produtos = ProdutoDataHelper.createListProduto();
		
		when(produtoRepository.buscaTodosOsProdutos()).thenReturn(produtos);
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaTodosOsProdutos();
	
		verify(produtoRepository, times(1)).buscaTodosOsProdutos();

		assertThat(response).isNotEmpty();
		assertEquals(4, response.size());
		assertEquals(produtos.get(0).getIdProduto(), response.get(0).getIdProduto());
	}
	
	@Test
	@DisplayName("Busca Todos os Produto - retorna lista Vazia")
	void listaProduto_retornaListaVazia() {
		when(produtoRepository.buscaTodosOsProdutos()).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaTodosOsProdutos();
	
		verify(produtoRepository, times(1)).buscaTodosOsProdutos();

		assertThat(response).isEmpty();
	}
	
	@Test
	@DisplayName("Busca Produtos Por Categoria")
	void listaProdutoPorCategoria_retornaListaDeProdutos() {
		List<Produto> produtos = ProdutoDataHelper.createListProdutoCategoria();
		String categoria = "ELETRONICO";
		when(produtoRepository.buscaProdutosPorCategoria(any())).thenReturn(produtos);
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorCategoria(categoria);
	
		verify(produtoRepository, times(1)).buscaProdutosPorCategoria(Categoria.ELETRONICO);

		assertThat(response).isNotEmpty();
		assertEquals(4, response.size());
		assertEquals(Categoria.ELETRONICO, response.get(0).getCategoria());
	}
	
	@Test
	@DisplayName("Busca Produtos Por Categoria - Retorna lista vazia")
	void listaProdutoPorCategoria_retornaListaVazia() {
		String categoria = "ELETRONICO";
		when(produtoRepository.buscaProdutosPorCategoria(any())).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorCategoria(categoria);
	
		verify(produtoRepository, times(1)).buscaProdutosPorCategoria(Categoria.ELETRONICO);

		assertThat(response).isEmpty();
	}
	
	@Test
	@DisplayName("Busca Produtos Por Categoria - Retorna Erro")
	void listaProdutoPorCategoria_comCategoriaInvalida_retornaErro() {
		String categoria = "EXEMPLO";

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.buscaProdutosPorCategoria(categoria));
		
		assertEquals("Nenhum Produto encontrado para esta categoria.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}

	@Test
	@DisplayName("Busca Todos os Produto por Nome")
	void listaProdutoPorNome_retornaListaDeProdutos() {
		List<Produto> produtos = ProdutoDataHelper.createListProduto();
		String nome = "Produto";
		when(produtoRepository.buscaTodosOsProdutos()).thenReturn(produtos);
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorNome(nome);
	
		verify(produtoRepository, times(1)).buscaTodosOsProdutos();

		assertThat(response).isNotEmpty();
		assertEquals(4, response.size());
	}
	
	@Test
	@DisplayName("Busca Todos os Produto por Nome - retorna lista Vazia")
	void listaProdutoPorNome_retornaListaVazia() {
		String nome = "Exemplo";
		when(produtoRepository.buscaTodosOsProdutos()).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorNome(nome);
	
		verify(produtoRepository, times(1)).buscaTodosOsProdutos();

		assertThat(response).isEmpty();
	}

	@Test
	@DisplayName("Deleta Produto Por Id")
	void deletaProduto_comIdValido_semRetorno() {
		Cliente cliente = ClienteDataHelper.createCliente();
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		String email = "exemplo@gmail.com";

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).deletaProduto(produto);
		
		produtoApplicationService.deletaProdutoPorId(email, idProduto);
	
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).deletaProduto(produto);
	}
	
	@Test
	@DisplayName("Deleta Produto com IdInvalido")
	void deletaProduto_comIdInvalido_retornoErro() {
		Cliente cliente = ClienteDataHelper.createCliente();
		UUID idProduto = UUID.randomUUID();
		String email = "exemplo@gmail.com";
		
		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.deletaProdutoPorId(email, idProduto));

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);

		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	
	@Test
	@DisplayName("Edita Produto")
	void editaProduto_comDadosValidos_alteraProduto() {
		Cliente cliente = ClienteDataHelper.createCliente();
		Produto produto = ProdutoDataHelper.createProduto();
		EditaProdutoRequest request = ProdutoDataHelper.editaProdutoRequest();
		String email = cliente.getEmail();
		UUID idProduto = produto.getIdProduto();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));

		produtoApplicationService.editaProdutoPorId(email, idProduto, request);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).editaProduto(produto, request);
	}
	
	@Test
	@DisplayName("Edita Produto com IdInvalido - retorna Erro")
	void editaProduto_comIdInvalido_retornaErro() {
		Cliente cliente = ClienteDataHelper.createCliente();
		EditaProdutoRequest request = ProdutoDataHelper.editaProdutoRequest();
		String email = cliente.getEmail();
		UUID idProduto = UUID.randomUUID();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.editaProdutoPorId(email, idProduto, request));

		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Altera promocao do Produto")
	void alteraPromocaoDoProduto_comPromocaoValida_alteraPromocao() {
		Cliente cliente = ClienteDataHelper.createCliente();
		Produto produto = mock(Produto.class);
		AlteraPromocaoProdutoRequest request = ProdutoDataHelper.alteraPromocaoProdutoRequest();
		String email = cliente.getEmail();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();
		PromocaoProduto promocao = PromocaoProduto.LEVE_2_PAGUE_1;

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).alteraPromocaoDoProduto(produto, promocao);

		produtoApplicationService.alteraPromocaoDoProdutoPorId(email, idProduto, request);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produto, times(1)).alteraStatusPromocao(any());
		verify(produtoRepository, times(1)).alteraPromocaoDoProduto(produto, promocao);
	}
	
	@Test
	@DisplayName("Busca Produto com Promocao")
	void listaProdutoComPromocao_retornaListaDeProdutos() {
		List<Produto> produtos = ProdutoDataHelper.createListProdutoComPromocao();
		when(produtoRepository.buscaProdutoComPromocao()).thenReturn(produtos);
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutoComPromocao();
	
		verify(produtoRepository, times(1)).buscaProdutoComPromocao();

		assertThat(response).isNotEmpty();
		assertEquals(4, response.size());
		assertEquals(PromocaoProdutoStatus.ATIVO, response.get(0).getStatusPromocao());
	}
	
	@Test
	@DisplayName("Aplica promocao ao Produto")
	void aplicaPromocaoAoProduto_comPromocaoValida_alteraPromocao() {
		Cliente cliente = ClienteDataHelper.createCliente();
		Produto produto = mock(Produto.class);
		PromocaoProdutoRequest request = ProdutoDataHelper.promocaoProdutoRequest();
		String email = cliente.getEmail();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).aplicaPromocaoAoProduto(produto, request);

		Produto response = ProdutoDataHelper.createProdutoComPromocao();

		produtoApplicationService.aplicaPromocaoAoProduto(email, idProduto, request);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).aplicaPromocaoAoProduto(produto, request);
	
		assertEquals(PromocaoProdutoStatus.ATIVO, response.getStatusPromocao());
	}
	
	@Test
	@DisplayName("Encerra promocao do Produto")
	void encerraPromocaoAoProduto_comPromocaoValida_alteraPromocao() {
		Cliente cliente = ClienteDataHelper.createCliente();
		Produto produto = mock(Produto.class);
		String email = cliente.getEmail();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();

		when(clienteRepository.detalhaClientePorEmail(any())).thenReturn(cliente);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).encerraPromocaoDoProduto(produto);

		produtoApplicationService.encerraPromocaoDoProduto(email, idProduto);
	
		verify(clienteRepository, times(1)).detalhaClientePorEmail(email);
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produto).validaPromocao();
		verify(produtoRepository, times(1)).encerraPromocaoDoProduto(produto);
	
	}
}
