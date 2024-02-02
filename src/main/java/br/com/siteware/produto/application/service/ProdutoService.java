package br.com.siteware.produto.application.service;

import java.util.UUID;

import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;

public interface ProdutoService {

	ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest);

	ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto);

}
