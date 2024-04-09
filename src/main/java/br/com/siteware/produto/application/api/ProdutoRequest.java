package br.com.siteware.produto.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ProdutoRequest {
	@NotNull(message = "Campo Categoria não pode está vazio.")
	private String categoria;
	@NotNull(message = "Campo Promocao não pode está vazio.")
	private Integer promocao;
	@NotNull(message = "Campo Estoque não pode está vazio.")
	private Integer estoque;
	@NotBlank(message = "Campo Nome não pode está vazio.")
	private String nome;
	@NotBlank(message = "Campo Descrição não pode está vazio.")
	@Size(max = 255, min = 3)
	private String descricao;
	@NotNull(message = "Campo Preco não pode ser nulo.")
	private Double preco;
}
