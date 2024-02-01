package br.com.siteware.produto;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Produto {
	
	@Id
	private UUID idProduto;
	@NotNull
	private String nome;
	@NotBlank
	@Size(max = 255, min = 3)
	private String descricao;
	@NotNull
	private Double preco;
	
	public Produto(UUID idProduto, @NotNull String nome, @NotBlank @Size(max = 255, min = 3) String descricao,
			@NotNull Double preco) {
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

}
