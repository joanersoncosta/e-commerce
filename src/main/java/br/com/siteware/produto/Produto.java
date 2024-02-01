package br.com.siteware.produto;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.handler.APIException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Produto {

	@Id
	private UUID idProduto;
	@NotNull
	private Categoria categoria;
	@NotNull
	private String nome;
	@NotBlank
	@Size(max = 255, min = 3)
	private String descricao;
	@NotNull
	private Double preco;

	public Produto(String categoriaRequest, String nome, String descricao, Double preco) {
		this.idProduto = UUID.randomUUID();
		this.categoria = retornaCategoria(categoriaRequest);
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	private Categoria retornaCategoria(String categoria) {
		return Categoria.validaCategoria(categoria)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Categoria inválida, digite novamente."));
	}

}
