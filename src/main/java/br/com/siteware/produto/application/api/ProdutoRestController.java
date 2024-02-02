package br.com.siteware.produto.application.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.produto.application.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ProdutoRestController implements ProdutoAPI {
	private final ProdutoService produtoService;

	@Override
	public ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		ProdutoIdResponse produtoIdResponse = produtoService.cadastraProduto(email, produtoRequest);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoIdResponse;
	}

	@Override
	public ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		ProdutoDetalhadoResponse produtoDetalhadoResponse = produtoService.buscaProdutoPorId(idProduto);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoDetalhadoResponse;

	}

}
