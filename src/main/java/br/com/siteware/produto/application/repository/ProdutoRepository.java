package br.com.siteware.produto.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.domain.Produto;

public interface ProdutoRepository {

	Produto salva(Produto produto);

	Optional<Produto> detalhaProdutoPorId(UUID idProduto);

	List<Produto> buscaTodosProdutos();

	List<Produto> buscaProdutosPorCategoria(Categoria categoriaVarida);

}
