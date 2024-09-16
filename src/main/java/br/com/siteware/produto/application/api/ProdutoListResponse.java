package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.domain.EstoqueProdutoStatus;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProdutoListResponse {
	@Schema(description = "Este é o ID do Cliente")
	private UUID idProduto;
	@Schema(description = "Esta é a catégoria do produto", example = "PEPELARIA")
	private Categoria categoria;
	@Schema(description = "Este é o tipo de promoção do produto", example = "LEVE_3_PAGUE_10_REAIS")
	private PromocaoProduto promocao;
	@Schema(description = "Este é o status da promoção deste produto", example = "ATIVO")
	private PromocaoProdutoStatus statusPromocao;
	@Schema(description = "Este é o status da estoque deste produto", example = "DISPONIVEL")
	private EstoqueProdutoStatus statusEstoque;
	@Schema(description = "Este é o estoque do produto", example = "20")
	private Integer estoque;
	@Schema(description = "Esta é quantidade de produtos vendidos", example = "10")
	private Integer produtosVendidos;
	@Schema(description = "Este é o desconto do produto", example = "0%")
	private String desconto;
	@Schema(description = "Este é o estoque do produto", example = "Grampeador")
	private String nome;
	@Schema(description = "Esta é a descricao do produto", example = "Teste Descrição")
	private String descricao;
	@Schema(description = "Este é o preco do produto", example = "6.00")
	private Double preco;
	
	private ProdutoListResponse(Produto produto) {
		this.idProduto = produto.getIdProduto();
		this.categoria = produto.getCategoria();
		this.promocao = produto.getPromocao();
		this.statusEstoque = produto.getStatusEstoque();
		this.statusPromocao = produto.getStatusPromocao();
		this.estoque = produto.getEstoque();
		this.produtosVendidos  = produto.getProdutosVendidos();
		this.desconto = produto.getDesconto() + "%";
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
