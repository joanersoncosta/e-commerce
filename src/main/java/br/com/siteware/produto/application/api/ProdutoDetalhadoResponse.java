package br.com.siteware.produto.application.api;

import java.util.UUID;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.Getter;

@Getter
public class ProdutoDetalhadoResponse {
	
	private UUID idProduto;
	private Categoria categoria;
	private PromocaoProduto promocao;
	private String nome;
	private String descricao;
	private Double preco;
	
	private ProdutoDetalhadoResponse(Produto produto) {
		this.idProduto = produto.getIdProduto();
		this.categoria = produto.getCategoria();
		this.promocao = produto.getPromocao();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
	}

	public static ProdutoDetalhadoResponse converte(Produto produto) {
		return new ProdutoDetalhadoResponse(produto);
	}
}
