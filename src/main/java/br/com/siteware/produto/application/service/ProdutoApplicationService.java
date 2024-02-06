package br.com.siteware.produto.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoListResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProdutoApplicationService implements ProdutoService {
	private final ProdutoRepository produtoRepository;
	private final ClienteRepository clienteRepository;
	
	@Override
	public ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoApplicationService - cadastraProduto");
		clienteRepository.detalhaClientePorEmail(email);
		Produto produto = produtoRepository.salva(new Produto(produtoRequest));
		log.info("[finaliza] ProdutoApplicationService - cadastraProduto");
		return ProdutoIdResponse.builder().idProduto(produto.getIdProduto()).build();
	}

	@Override
	public ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutoPorId");
		var produtoResponse = produtoRepository.detalhaProdutoPorId(idProduto)
				.map(ProdutoDetalhadoResponse::converte)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
		log.info("[finaliza] ProdutoRestController - buscaProdutoPorId");
		return produtoResponse;

	}

	@Override
	public List<ProdutoListResponse> buscaTodosOsProdutos() {
		log.info("[inicia] ProdutoRestController - buscaTodosProdutos");
		List<Produto> produtos = produtoRepository.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaTodosProdutos");
		return ProdutoListResponse.converte(produtos);
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorCategoria(String categoria) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorCategoria");
		Categoria categoriaValida = Categoria.validaCategoria(categoria.toUpperCase())
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Nenhum Produto encontrado para esta categoria."));
		List<Produto> produtos = produtoRepository.buscaProdutosPorCategoria(categoriaValida);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorCategoria");
		return ProdutoListResponse.converte(produtos);
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorNome(String nomeProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorNome");
		List<Produto> produtos = produtoRepository.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorNome");
		return ProdutoListResponse.converte(produtos, nomeProduto);
	}

}
