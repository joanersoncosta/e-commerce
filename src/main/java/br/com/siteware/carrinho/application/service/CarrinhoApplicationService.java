package br.com.siteware.carrinho.application.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.carrinho.application.api.CarrinhoIdResponse;
import br.com.siteware.carrinho.application.api.CarrinhoListResponse;
import br.com.siteware.carrinho.application.api.CarrinhoRequest;
import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CarrinhoApplicationService implements CarrinhoService {
	private final ProdutoRepository produtoRepository;
	private final ClienteRepository clienteRepository;
	private final CarrinhoRepository carrinhoRepository;

	@Override
	public CarrinhoIdResponse adicionaProdutoAoCarrinho(String email, CarrinhoRequest carrinhoRequest) {
		log.info("[inicia] CarrinhoApplicationService - adicionaProdutoAoCarrinho");
		log.info("[email] {}", email);
		Cliente cliente = clienteRepository.detalhaClientePorEmail(email);
		Produto produto = produtoRepository.detalhaProdutoPorId(carrinhoRequest.getIdProduto())
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
		Carrinho carrinho = carrinhoRepository.salva(new Carrinho(cliente.getIdCliente(), produto, carrinhoRequest.getQuantidade()));
		log.info("[finaliza] CarrinhoApplicationService - adicionaProdutoAoCarrinho");
		return CarrinhoIdResponse.builder().idCarrinho(carrinho.getIdCarrinho()).build();
	}

	@Override
	public List<CarrinhoListResponse> listaCarrinhoDoCliente(String email) {
		log.info("[inicia] CarrinhoApplicationService - listaCarrinhoDoCliente");
		log.info("[email] {}", email);
		Cliente cliente = clienteRepository.detalhaClientePorEmail(email);
		List<Carrinho > carrinhoDoCliente = carrinhoRepository.listaCarrinhoDoCliente(cliente.getIdCliente());
		log.info("[finaliza] CarrinhoApplicationService - listaCarrinhoDoCliente");
		return CarrinhoListResponse.converte(carrinhoDoCliente);
	}

}
