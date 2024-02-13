package br.com.siteware;

import java.util.List;
import java.util.UUID;

import br.com.siteware.carrinho.application.api.CarrinhoRequest;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.produto.domain.Produto;

public class CarrinhoDataHelper {
	private static final UUID ID_CARRINHO = UUID.fromString("46a007c7-2321-4e1b-9469-b780fda14571");
	private static final UUID ID_PRODUTO = ProdutoDataHelper.createProduto().getIdProduto();
	private static final UUID ID_CLIENTE = UUID.fromString("8d58875d-2455-4075-8b5d-57c73fcf1241");
	private static final Produto produto = ProdutoDataHelper.createProduto();

	public static Carrinho createCarrinho() {
		return Carrinho.builder().idCarrinho(ID_CARRINHO).idCliente(ID_CLIENTE).idProduto(ID_PRODUTO)
				.promocao(produto.getPromocao()).nome(produto.getNome()).descricao(produto.getDescricao())
				.preco(produto.getPreco()).quantidade(1).subTotal(704.45).build();
	}

	public static CarrinhoRequest carrinhoRequest() {
		return new CarrinhoRequest(ID_PRODUTO, 1);
	}

	public static CarrinhoRequest carrinhoRequestInvalido() {
		return new CarrinhoRequest(UUID.randomUUID(), 1);
	}

	public static List<Carrinho> createListCarrinho() {
		List<Produto> produtos = ProdutoDataHelper.createListProduto();
		return List.of(
				Carrinho.builder().idCarrinho(UUID.randomUUID()).idCliente(ID_CLIENTE).idProduto(ID_PRODUTO)
						.promocao(produto.getPromocao()).nome(produto.getNome()).descricao(produto.getDescricao())
						.preco(produto.getPreco()).quantidade(1).subTotal(704.45).build(),
				Carrinho.builder().idCarrinho(UUID.randomUUID()).idCliente(ID_CLIENTE)
						.idProduto(produtos.get(1).getIdProduto()).promocao(produtos.get(1).getPromocao())
						.nome(produtos.get(1).getNome()).descricao(produtos.get(1).getDescricao())
						.preco(produto.getPreco()).quantidade(1).subTotal(704.45).build(),
				Carrinho.builder().idCarrinho(UUID.randomUUID()).idCliente(ID_CLIENTE)
						.idProduto(produtos.get(3).getIdProduto()).promocao(produtos.get(3).getPromocao())
						.nome(produtos.get(3).getNome()).descricao(produtos.get(3).getDescricao())
						.preco(produto.getPreco()).quantidade(1).subTotal(704.45).build());
	}
}
