package br.com.siteware.produto.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.siteware.ClienteDataHelper;
import br.com.siteware.ProdutoDataHelper;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;

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

}
