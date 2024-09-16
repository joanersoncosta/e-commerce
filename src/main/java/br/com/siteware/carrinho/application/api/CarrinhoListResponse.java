package br.com.siteware.carrinho.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CarrinhoListResponse {
	@Schema(description = "Este é o ID do Carrinho")
	private UUID idCarrinho;
	@Schema(description = "Este é o ID do Produto")
	private UUID idProduto;
	@Schema(description = "Quantidade de Produtos no Carrinho", example = "LEVE_3_PAGUE_10_REAIS")
	private PromocaoProduto promocao;
	@Schema(description = "Este é o nome do Produto", example = "Grampeador")
	private String nome;
	@Schema(description = "Esta é a descricao do Produto", example = "12")
	private String descricao;
	@Schema(description = "Este é o preço do Produto", example = "6")
	private Double preco;
	@Schema(description = "Esta é quantidade do Produto", example = "3")
	private Integer quantidade;
	@Schema(description = "Este é o subTotal deste Produto", example = "10")
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
