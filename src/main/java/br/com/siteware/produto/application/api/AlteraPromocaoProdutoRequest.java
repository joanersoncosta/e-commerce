package br.com.siteware.produto.application.api;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AlteraPromocaoProdutoRequest {
	@NotNull(message = "Campo Promocao não pode está vazio.")
	@Schema(description = "Esta é a promocao do produto", example = "1 até 4")
	private Integer promocao; 
}
