package br.com.siteware.produto.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.http.HttpStatus;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;
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
	@Indexed
	private String emailAssociadoAoAdmin;
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
	private Integer desconto;
	private Double precoOriginal;
	private LocalDateTime dataCadastroProduto;
	private LocalDateTime dataModificacaoProduto;

	public Produto(String emailAssociadoAoAdmin, ProdutoRequest produtoRequest) {
		this.idProduto = UUID.randomUUID();
		this.emailAssociadoAoAdmin = emailAssociadoAoAdmin;
		this.categoria = retornaCategoria(produtoRequest.getCategoria());
		this.promocao = retornaPromocao(produtoRequest.getPromocao());
		this.statusPromocao = statusPromocao(promocao);
		this.statusEstoque = EstoqueProdutoStatus.DISPONIVEL;
		this.estoque = produtoRequest.getEstoque();
		this.nome = produtoRequest.getNome();
		this.descricao = produtoRequest.getDescricao();
		this.preco = produtoRequest.getPreco();
		this.produtosVendidos = 0;
		this.desconto = 0;
		this.precoOriginal = produtoRequest.getPreco();
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
		if (promocao == PromocaoProduto.NENHUM) {
			this.statusPromocao = PromocaoProdutoStatus.INATIVO;
		} else {
			this.statusPromocao = PromocaoProdutoStatus.ATIVO;
		}
	}

	public void incrementaProdutosVendidos(Integer quantidade) {
		this.produtosVendidos += quantidade;
	}

	public void atualizaProdutosVendidos(Integer quantidadeAtual, Integer novaQuantidade) {
		this.produtosVendidos = retornaQuantidade(quantidadeAtual) + novaQuantidade;
	}

	public Integer retornaQuantidade(Integer quantidadeAtual) {
		return Math.abs(this.produtosVendidos - quantidadeAtual);
	}
	
	public void reduzProdutosVendidos(Integer quantidade) {
		this.produtosVendidos -= quantidade;
	}

	public void validaEstoque(Integer quantidade) {
		if (this.estoque < (produtosVendidos + quantidade)) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Não possuimos esta quantidade em estoque.");
		}
	}

	public void validaProdutosVendidosDOEstoque(Integer quantidade, Integer novaQuantidade) {
		Integer ValidaQuantidade = retornaQuantidade(quantidade) + novaQuantidade;
		if (estoque < ValidaQuantidade) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Não possuimos esta quantidade em estoque.");
		}
	}

	public void validaStatusEstoque() {
		if (this.estoque.equals(produtosVendidos)) {
			this.statusEstoque = EstoqueProdutoStatus.INDISPONIVEL;
		}
	}

	public void validaPromocao() {
		if (!this.promocao.equals(PromocaoProduto.PROMOCAO)) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Este Produto não possui promoção.");
		}

	}

	public void pertenceAoUsuario(String username) {
		if(!this.emailAssociadoAoAdmin.equals(username)) {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não é o dono do produto.");
		}
	}

}