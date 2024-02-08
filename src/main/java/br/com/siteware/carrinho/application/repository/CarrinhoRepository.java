package br.com.siteware.carrinho.application.repository;

import java.util.List;
import java.util.UUID;

import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.produto.domain.Produto;

public interface CarrinhoRepository {

	Carrinho salva(Carrinho carrinho);

	List<Carrinho> listaCarrinhoDoCliente(UUID idCliente);

	Carrinho buscaCarrinhoPorId(UUID idCarrinho);

	void removeCarrinho(Carrinho carrinho);

	void atualizaCarrinho(Carrinho carrinho);

	void atualizaProdutosVendidos(Carrinho carrinho, Produto produto);

}
