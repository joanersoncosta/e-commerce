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
public class EditaProdutoRequest {
	@NotBlank(message = "Campo Nome não pode está vazio.")
	@Schema(description = "Este é o estoque do produto", example = "Grampeador")
	private String nome;
	@NotBlank(message = "Campo Descrição não pode está vazio.")
	@Size(max = 255, min = 3)
	@Schema(description = "Esta é a descricao do produto", example = "Teste Descrição")
	private String descricao;
	@NotNull(message = "Campo Preco não pode ser nulo.")
	@Schema(description = "Este é o preco do produto", example = "5.50")
	private Double preco;
}