package br.com.siteware.produto.application.service;

import java.util.List;
import java.util.UUID;

import br.com.siteware.produto.application.api.AlteraPromocaoProdutoRequest;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoListResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.api.PromocaoProdutoRequest;
import jakarta.validation.Valid;

public interface ProdutoService {

	ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest);

	ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto);

	List<ProdutoListResponse> buscaTodosOsProdutos();

	List<ProdutoListResponse> buscaProdutosPorCategoria(String categoria);

	List<ProdutoListResponse> buscaProdutosPorNome(String nomeProduto);

	void deletaProdutoPorId(String email, UUID idProduto);

	void editaProdutoPorId(String email, UUID idProduto, EditaProdutoRequest editaProduto);

	void alteraPromocaoDoProdutoPorId(String email, UUID idProduto, AlteraPromocaoProdutoRequest editaPromocaoProduto);

	List<ProdutoListResponse> buscaProdutoComPromocao();

	void aplicaPromocaoAoProduto(String email, UUID idProduto, PromocaoProdutoRequest promocaoRequest);

	void encerraPromocaoDoProduto(String email, UUID idProduto);

}
