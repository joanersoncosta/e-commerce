package br.com.siteware.produto.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ProdutoRequest {
	@NotNull(message = "Campo Categoria não pode está vazio.")
	@Schema(description = "Esta é a categoria do produto", example = "PAPELARIA")
	private String categoria;
	@NotNull(message = "Campo Promocao não pode está vazio.")
	@Schema(description = "Esta é a promocao do produto", example = "1 até 4")
	private Integer promocao;
	@NotNull(message = "Campo Estoque não pode está vazio.")
	@Schema(description = "Este é o estoque do produto", example = "20")
	private Integer estoque;
	@NotBlank(message = "Campo Nome não pode está vazio.")
	@Schema(description = "Este é o estoque do produto", example = "Grampeador")
	private String nome;
	@NotBlank(message = "Campo Descrição não pode está vazio.")
	@Size(max = 255, min = 3)
	@Schema(description = "Esta é a descricao do produto", example = "Teste Descrição")
	private String descricao;
	@NotNull(message = "Campo Preco não pode ser nulo.")
	@Schema(description = "Este é o preco do produto", example = "6.00")
	private Double preco;
}
