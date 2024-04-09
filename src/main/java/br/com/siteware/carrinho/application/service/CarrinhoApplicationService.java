package br.com.siteware.carrinho.application.service;

import java.util.List;
import java.util.UUID;

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
		Cliente cliente = clienteRepository.detalhaClientePorEmail(email);
		log.info("[cliente] {}", cliente);
		Produto produto = produtoRepository.detalhaProdutoPorId(carrinhoRequest.getIdProduto())
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.validaEstoque(carrinhoRequest.getQuantidade());
		produto.incrementaProdutosVendidos(carrinhoRequest.getQuantidade());
		produto.validaStatusEstoque();
		produtoRepository.salva(produto);
		Carrinho carrinho = carrinhoRepository
				.salva(new Carrinho(cliente.getIdCliente(), produto, carrinhoRequest.getQuantidade()));
		log.info("[finaliza] CarrinhoApplicationService - adicionaProdutoAoCarrinho");
		return CarrinhoIdResponse.builder().idCarrinho(carrinho.getIdCarrinho()).build();
	}

	@Override
	public List<CarrinhoListResponse> listaCarrinhoDoCliente(String email, UUID idCliente) {
		log.info("[inicia] CarrinhoApplicationService - listaCarrinhoDoCliente");
		Cliente clienteEmail = clienteRepository.detalhaClientePorEmail(email);
		log.info("[clienteEmail] {}", clienteEmail);
		Cliente cliente = clienteRepository.detalhaClientePorId(idCliente)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
		cliente.pertenceAoCliente(clienteEmail);
		List<Carrinho> carrinhoDoCliente = carrinhoRepository.listaCarrinhoDoCliente(cliente.getIdCliente());
		log.info("[finaliza] CarrinhoApplicationService - listaCarrinhoDoCliente");
		return CarrinhoListResponse.converte(carrinhoDoCliente);
	}

	public Carrinho detalhaCarrinho(UUID idCarrinho) {
		log.info("[inicia] CarrinhoApplicationService - detalhaCarrinho");
		log.info("[idCarrinho] {}", idCarrinho);
		Carrinho carrinho = carrinhoRepository.buscaCarrinhoPorId(idCarrinho)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Carrinho não encontrado."));
		log.info("[finaliza] CarrinhoApplicationService - detalhaCarrinho");
		return carrinho;
	}

	@Override
	public void removeCarrinho(String email, UUID idCarrinho) {
		log.info("[inicia] CarrinhoApplicationService - removeCarrinho");
		Cliente clienteEmail = clienteRepository.detalhaClientePorEmail(email);
		log.info("[clienteEmail] {}", clienteEmail);
		log.info("[idCarrinho] {}", idCarrinho);
		Carrinho carrinho = carrinhoRepository.buscaCarrinhoPorId(idCarrinho)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Carrinho não encontrado."));
		Produto produto = produtoRepository.detalhaProdutoPorId(carrinho.getIdProduto()).orElseThrow(
				() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado para este carrinho!"));
		carrinho.pertenceCliente(clienteEmail);
		carrinhoRepository.atualizaProdutosVendidos(carrinho, produto);
		carrinhoRepository.removeCarrinho(carrinho);
		log.info("[finaliza] CarrinhoApplicationService - removeCarrinho");
	}

	@Override
	public void editaCarrinho(String email, UUID idCarrinho, Integer quantidade) {
		log.info("[inicia] CarrinhoApplicationService - editaCarrinho");
		Cliente clienteEmail = clienteRepository.detalhaClientePorEmail(email);
		log.info("[clienteEmail] {}", clienteEmail);
		log.info("[idCarrinho] {}", idCarrinho);
		Carrinho carrinho = carrinhoRepository.buscaCarrinhoPorId(idCarrinho)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Carrinho não encontrado."));
		Produto produto = produtoRepository.detalhaProdutoPorId(carrinho.getIdProduto()).orElseThrow(
				() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado para este carrinho!"));
		carrinho.pertenceCliente(clienteEmail);
		produto.validaProdutosVendidosDOEstoque(carrinho.getQuantidade(), quantidade);
		produto.atualizaProdutosVendidos(carrinho.getQuantidade(), quantidade);
		produto.validaStatusEstoque();
		carrinho.atualizaCarrinho(quantidade);
		carrinhoRepository.atualizaCarrinho(carrinho);
		produtoRepository.salva(produto);
		log.info("[finaliza] CarrinhoApplicationService - editaCarrinho");
	}

}
