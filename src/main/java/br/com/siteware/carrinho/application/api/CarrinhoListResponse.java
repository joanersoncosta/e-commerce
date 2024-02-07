package br.com.siteware.carrinho.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.Getter;

@Getter
public class CarrinhoListResponse {
	
	private UUID idCarrinho;
	private UUID idProduto;
	private PromocaoProduto promocao;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer quantidade;
	private Double subTotal;
	
	public CarrinhoListResponse(Carrinho carrinho) {
		this.idCarrinho = carrinho.getIdCarrinho();
		this.idProduto = carrinho.getIdProduto();
		this.promocao = carrinho.getPromocao();
		this.nome = carrinho.getNome();
		this.descricao = carrinho.getDescricao();
		this.preco = carrinho.getPreco();
		this.quantidade = carrinho.getQuantidade();
		this.subTotal = carrinho.getSubTotal();
	}
	
	public static List<CarrinhoListResponse> converte(List<Carrinho> carrinho) {
		return carrinho.stream()
				.map(CarrinhoListResponse::new).collect(Collectors.toList());
	}

}
