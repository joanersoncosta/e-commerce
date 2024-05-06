package br.com.siteware.carrinho.domain;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "Carrinho")
public class Carrinho {

	@Id
	private UUID idCarrinho;
	@Indexed
	private UUID idCliente;
	@NotNull
	private UUID idProduto;
	private PromocaoProduto promocao;
	private String nome;
	private String descricao;
	private Double preco;
	@NotNull
	private Integer quantidade;
	private Double subTotal;
	
	public Carrinho(UUID idCliente, Produto produto, Integer quantidade, Double subTotal) {
		this.idCarrinho = UUID.randomUUID();
		this.idCliente = idCliente;
		this.idProduto = produto.getIdProduto();
		this.promocao = produto.getPromocao();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.quantidade = quantidade;
		validaQuantidade(quantidade);
		this.subTotal = subTotal;
	}

	private void validaQuantidade(Integer quantidade) {
	    if (quantidade <= 0) {
	        throw APIException.build(HttpStatus.BAD_REQUEST, "A quantidade deve ser maior que zero.");
	    }		
	}

	public void pertenceCliente(Cliente clienteEmail) {
		if (!this.idCliente.equals(clienteEmail.getIdCliente())) {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Cliente não autorizado.");
		}
	}

	public void atualizaCarrinho(Integer quantidade, Double subTotal) {
		validaQuantidade(quantidade);
		this.quantidade = quantidade;
	    this.subTotal = subTotal;
	}
	
}