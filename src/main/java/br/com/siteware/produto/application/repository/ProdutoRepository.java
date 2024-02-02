package br.com.siteware.produto.application.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.siteware.produto.domain.Produto;

public interface ProdutoRepository {

	Produto salva(Produto produto);

	Optional<Produto> detalhaProdutoPorId(UUID idProduto);

}
