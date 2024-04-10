package br.com.siteware.produto.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;

public interface ProdutoRepository {

	Produto salva(Produto produto);

	Optional<Produto> detalhaProdutoPorId(UUID idProduto);

	List<Produto> buscaTodosOsProdutos();

	List<Produto> buscaProdutosPorCategoria(Categoria categoriaValida);

	void deletaProduto(Produto produto);

	void editaProduto(Produto produto, EditaProdutoRequest request);

	void alteraPromocaoDoProduto(Produto produto, PromocaoProduto promocao);

	List<Produto> buscaProdutoComPromocao();

	void aplicaPromocaoAoProduto(Produto produto, Integer percentualDesconto);

	void encerraPromocaoDoProduto(Produto produto);

}