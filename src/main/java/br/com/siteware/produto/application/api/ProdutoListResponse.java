package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.stream.Collectors;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProdutoListResponse {
	private Categoria categoria;
	private PromocaoProduto promocao;
	private String nome;
	private String descricao;
	private Double preco;

	private ProdutoListResponse(Produto produto) {
		this.categoria = produto.getCategoria();
		this.promocao = produto.getPromocao();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
	}

	public static List<ProdutoListResponse> converte(List<Produto> produto) {
		return produto.stream()
				.map(ProdutoListResponse::new).collect(Collectors.toList());
	}
	
	public static List<ProdutoListResponse> converte(List<Produto> produto, String nome) {
		return produto.stream()
                .filter(n -> n.getNome().toLowerCase().contains(nome.toLowerCase()))				
				.map(ProdutoListResponse::new).collect(Collectors.toList());
	}
}
