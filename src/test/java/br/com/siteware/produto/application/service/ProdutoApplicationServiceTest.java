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

import br.com.siteware.CredencialDataHelpher;
import br.com.siteware.ProdutoDataHelper;
import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.credencial.application.service.CredencialService;
import br.com.siteware.credencial.domain.Credencial;
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
	@Mock
	private CredencialService credencialService;
	
	@Test
	@DisplayName("Cadastra Produto com sucesso")
	void cadastraProduto_comDadosValidos_retornaProdutoIdResponse() {
		ProdutoRequest request = ProdutoDataHelper.createProdutorequest();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.salva(any())).thenReturn(new Produto(credencialUsuario.getUsername(), request));
		
		ProdutoIdResponse response = produtoApplicationService.cadastraProduto(credencialUsuario.getUsername(), request);
	
		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).salva(any());

		assertThat(response).isNotNull();
		assertEquals(ProdutoIdResponse.class, response.getClass());
	}
	
	@Test
	@DisplayName("Cadastra Produto com credencial nao autorizada")
	void cadastraProduto_comCredencialDeCliente_retornaAcessoNegado() {
		ProdutoRequest request = ProdutoDataHelper.createProdutorequest();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.cadastraProduto(credencialUsuario.getUsername(), request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
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
	@DisplayName("Busca Produto Por Id, retorna erro")
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
	@DisplayName("Busca Todos os Produto, retorna lista Vazia")
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
	@DisplayName("Busca Produtos Por Categoria, Retorna lista vazia")
	void listaProdutoPorCategoria_retornaListaVazia() {
		String categoria = "ELETRONICO";
		when(produtoRepository.buscaProdutosPorCategoria(any())).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorCategoria(categoria);
	
		verify(produtoRepository, times(1)).buscaProdutosPorCategoria(Categoria.ELETRONICO);

		assertThat(response).isEmpty();
	}
	
	@Test
	@DisplayName("Busca Produtos Por Categoria, Retorna Erro")
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
	@DisplayName("Busca Todos os Produto por Nome, retorna lista Vazia")
	void listaProdutoPorNome_retornaListaVazia() {
		String nome = "Exemplo";
		when(produtoRepository.buscaTodosOsProdutos()).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutosPorNome(nome);
	
		verify(produtoRepository, times(1)).buscaTodosOsProdutos();

		assertThat(response).isEmpty();
	}

	@Test
	@DisplayName("Deleta Produto com credencial nao autorizada")
	void deletaProduto_comCredencialDeCliente_retornaAcessoNegado() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.deletaProdutoPorId(credencialUsuario.getUsername(), idProduto));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Deleta Produto Por Id")
	void deletaProduto_comIdValido_semRetorno() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).deletaProduto(produto);
		
		produtoApplicationService.deletaProdutoPorId(credencialUsuario.getUsername(), idProduto);
	
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).deletaProduto(produto);
	}
	
	@Test
	@DisplayName("Deleta Produto com IdInvalido")
	void deletaProduto_comIdInvalido_retornoErro() {
		UUID idProduto = UUID.randomUUID();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();
		
		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.deletaProdutoPorId(email, idProduto));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);

		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Edita Produto")
	void editaProduto_comDadosValidos_alteraProduto() {
		Produto produto = ProdutoDataHelper.createProduto();
		EditaProdutoRequest request = ProdutoDataHelper.editaProdutoRequest();
		UUID idProduto = produto.getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();
		
		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));

		produtoApplicationService.editaProdutoPorId(email, idProduto, request);
	
		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).editaProduto(produto, request);
	}
	
	@Test
	@DisplayName("Edita Produto com IdInvalido, retorna Erro")
	void editaProduto_comIdInvalido_retornaErro() {
		EditaProdutoRequest request = ProdutoDataHelper.editaProdutoRequest();
		UUID idProduto = UUID.randomUUID();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.editaProdutoPorId(email, idProduto, request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Edita Produto com credencial nao autorizada")
	void editaProdutoProduto_comCredencialDeCliente_retornaAcessoNegado() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		EditaProdutoRequest request = ProdutoDataHelper.editaProdutoRequest();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.editaProdutoPorId(credencialUsuario.getUsername(), idProduto, request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Altera promocao do Produto")
	void alteraPromocaoDoProduto_comPromocaoValida_alteraPromocao() {
		Produto produto = mock(Produto.class);
		AlteraPromocaoProdutoRequest request = ProdutoDataHelper.alteraPromocaoProdutoRequest();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();
		PromocaoProduto promocao = PromocaoProduto.LEVE_2_PAGUE_1;
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).alteraPromocaoDoProduto(produto, promocao);

		produtoApplicationService.alteraPromocaoDoProdutoPorId(email, idProduto, request);
	
		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produto, times(1)).alteraStatusPromocao(any());
		verify(produtoRepository, times(1)).alteraPromocaoDoProduto(produto, promocao);
	}
	
	@Test
	@DisplayName("Altera Promocao do Produto com credencial nao autorizada")
	void alteraPromocaoDoProduto_comCredencialDeCliente_retornaAcessoNegado() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		AlteraPromocaoProdutoRequest request = ProdutoDataHelper.alteraPromocaoProdutoRequestComPromocaoInvalida();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.alteraPromocaoDoProdutoPorId(email, idProduto, request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Altera promocao do produto com Promocao Invalida, retorna erro")
	void alteraPromocaoDoProduto_comPromocaoInvalida_retornaErro() {
		AlteraPromocaoProdutoRequest request = ProdutoDataHelper.alteraPromocaoProdutoRequestComPromocaoInvalida();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();

		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.alteraPromocaoDoProdutoPorId(email, idProduto, request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Promoção invalida.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Altera promocao do Produto com IdProduto Invalido, retorna erro")
	void alteraPromocaoDoProduto_comIdInvalido_rtornaErro() {
		AlteraPromocaoProdutoRequest request = ProdutoDataHelper.alteraPromocaoProdutoRequest();
		UUID idProduto = UUID.randomUUID();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.alteraPromocaoDoProdutoPorId(email, idProduto, request));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Busca Produtos com Promocao")
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
	@DisplayName("Busca Produtos com Promocao, retorna Lista Vazia")
	void listaProdutoComPromocao_retornaListaVazia() {
		when(produtoRepository.buscaProdutoComPromocao()).thenReturn(Collections.emptyList());
		
		List<ProdutoListResponse> response = produtoApplicationService.buscaProdutoComPromocao();
	
		verify(produtoRepository, times(1)).buscaProdutoComPromocao();

		assertThat(response).isEmpty();
	}
	
	@Test
	@DisplayName("Aplica promocao ao Produto")
	void aplicaPromocaoAoProduto_comPromocaoValida_alteraPromocao() {
		Produto produto = ProdutoDataHelper.createProduto();
		PromocaoProdutoRequest request = ProdutoDataHelper.promocaoProdutoRequest();
		UUID idProduto = produto.getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).aplicaPromocaoAoProduto(produto, request.getPercentualDesconto());
		Produto response = ProdutoDataHelper.createProdutoComPromocao();
		produtoApplicationService.aplicaPromocaoAoProduto(email, idProduto, request.getPercentualDesconto());
	
		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produtoRepository, times(1)).aplicaPromocaoAoProduto(produto, request.getPercentualDesconto());
	
		assertEquals(PromocaoProdutoStatus.ATIVO, response.getStatusPromocao());
	}
	
	@Test
	@DisplayName("Aplica Promocao ao Produto com credencial nao autorizada")
	void aplicaPromocaoAoProduto_comCredencialDeCliente_retornaAcessoNegado() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		PromocaoProdutoRequest request = ProdutoDataHelper.promocaoProdutoRequest();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.aplicaPromocaoAoProduto(email, idProduto, request.getPercentualDesconto()));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Aplica promocao com IdProduto invalido, retorna erro")
	void aplicaPromocaoAoProduto_comidProdutoInvalido_retornaErro() {
		PromocaoProdutoRequest request = ProdutoDataHelper.promocaoProdutoRequest();
		UUID idProduto = UUID.randomUUID();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.aplicaPromocaoAoProduto(email, idProduto, request.getPercentualDesconto()));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	
	}
	
	@Test
	@DisplayName("Encerra promocao do Produto")
	void encerraPromocaoDoProduto_comPromocaoValida_alteraPromocao() {
		Produto produto = mock(Produto.class);
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));
		doNothing().when(produtoRepository).encerraPromocaoDoProduto(produto);

		produtoApplicationService.encerraPromocaoDoProduto(email, idProduto);
	
		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
		verify(produto).validaPromocao();
		verify(produtoRepository, times(1)).encerraPromocaoDoProduto(produto);
	}
	
	@Test
	@DisplayName("Encerra Promocao Do Produto com credencial nao autorizada")
	void encerraPromocaoDoProduto_comCredencialDeCliente_retornaAcessoNegado() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = produto.getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialCliente();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		
		APIException ex = assertThrows(APIException.class, () -> produtoApplicationService.encerraPromocaoDoProduto(email, idProduto));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());

		assertEquals("Acesso negado", ex.getMessage());
		assertEquals(HttpStatus.FORBIDDEN, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Encerra promocao com IdProduto invalido, retorna erro")
	void encerraPromocaoDoProduto_comIdProdutoInvalido_retornaErro() {
		UUID idProduto = UUID.randomUUID();

		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.empty());

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.encerraPromocaoDoProduto(email, idProduto));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Produto não encontrado.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusException());
	}
	
	@Test
	@DisplayName("Encerra promocao com Promocao Inexistente, retorna erro")
	void encerraPromocaoDoProduto_comPromocaoInexistente_retornaErro() {
		Produto produto = ProdutoDataHelper.createProduto();
		UUID idProduto = ProdutoDataHelper.createProduto().getIdProduto();
		Credencial credencialUsuario = CredencialDataHelpher.createCredencialAdmin();
		String email = credencialUsuario.getUsername();

		when(credencialService.buscaCredencialPorUsuario(any())).thenReturn(credencialUsuario);
		when(produtoRepository.detalhaProdutoPorId(any())).thenReturn(Optional.of(produto));

		APIException ex = assertThrows(APIException.class,
				() -> produtoApplicationService.encerraPromocaoDoProduto(email, idProduto));

		verify(credencialService, times(1)).buscaCredencialPorUsuario(any());
		verify(produtoRepository, times(1)).detalhaProdutoPorId(idProduto);
	
		assertEquals("Este Produto não possui promoção.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}
}
