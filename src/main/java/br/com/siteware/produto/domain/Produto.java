package br.com.siteware.produto.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.http.HttpStatus;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "Produto")
public class Produto {

	@MongoId
	private UUID idProduto;
	@NotNull
	private Categoria categoria;
	@NotNull
	private PromocaoProduto promocao;
	@NotNull
	private PromocaoProdutoStatus statusPromocao;
	@NotNull
	private EstoqueProdutoStatus statusEstoque;
	@NotNull
	private Integer estoque;
	@NotBlank
	private String nome;
	@NotBlank
	@Size(max = 255, min = 3)
	private String descricao;
	@NotNull
	private Double preco;
	private Integer produtosVendidos;
	private LocalDateTime dataCadastroProduto;
	private LocalDateTime dataModificacaoProduto;

	public Produto(ProdutoRequest produtoRequest) {
		this.idProduto = UUID.randomUUID();
		this.categoria = retornaCategoria(produtoRequest.getCategoria());
		this.promocao = retornaPromocao(produtoRequest.getPromocao());
		this.statusPromocao = statusPromocao(promocao);
		this.statusEstoque = EstoqueProdutoStatus.DISPONIVEL;
		this.estoque = produtoRequest.getEstoque();
		this.nome = produtoRequest.getNome();
		this.descricao = produtoRequest.getDescricao();
		this.preco = produtoRequest.getPreco();
		this.produtosVendidos = 0;
		this.dataCadastroProduto = LocalDateTime.now();
	}

	public PromocaoProdutoStatus statusPromocao(PromocaoProduto promocao) {
		return promocao == PromocaoProduto.NENHUM ? PromocaoProdutoStatus.INATIVO : PromocaoProdutoStatus.ATIVO;
	}

	private Categoria retornaCategoria(String categoria) {
		return Categoria.validaCategoria(categoria)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Categoria inválida, digite novamente."));
	}

	private PromocaoProduto retornaPromocao(Integer promocao) {
		return PromocaoProduto.validaPromocao(promocao)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Promoção inválida, digite novamente."));
	}

	public void alteraStatusPromocao(PromocaoProduto promocao) {
		if(promocao == PromocaoProduto.NENHUM){this.statusPromocao = PromocaoProdutoStatus.INATIVO;}
		else {this.statusPromocao = PromocaoProdutoStatus.ATIVO;}
	}
	
	public void incrementaProdutosVendidos() {
		this.produtosVendidos ++;
	}
	
	public void reduzProdutosVendidos() {
		this.produtosVendidos --;
	}

}
