package br.com.siteware.carrinho.application.repository;

import java.util.List;
import java.util.UUID;

import br.com.siteware.carrinho.domain.Carrinho;

public interface CarrinhoRepository {

	Carrinho salva(Carrinho carrinho);

	List<Carrinho> listaCarrinhoDoCliente(UUID idCliente);

	Carrinho buscaCarrinhoPorId(UUID idCarrinho);

	void removeCarrinho(Carrinho carrinho);

}
