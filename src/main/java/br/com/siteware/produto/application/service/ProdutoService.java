package br.com.siteware.produto.application.service;

import java.util.List;
import java.util.UUID;

import br.com.siteware.produto.application.api.AlteraPromocaoProdutoRequest;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoListResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;

public interface ProdutoService {

	ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest);

	ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto);

	List<ProdutoListResponse> buscaTodosOsProdutos();

	List<ProdutoListResponse> buscaProdutosPorCategoria(String categoria);

	List<ProdutoListResponse> buscaProdutosPorNome(String nomeProduto);

	void deletaProdutoPorId(UUID idProduto);

	void editaProdutoPorId(String email, UUID idProduto, EditaProdutoRequest editaProduto);

	void alteraPromocaoDoProdutoPorId(String email, UUID idProduto, AlteraPromocaoProdutoRequest editaPromocaoProduto);

	List<ProdutoListResponse> buscaProdutoComPromocao(String email);

}
