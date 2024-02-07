package br.com.siteware.produto.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AlteraPromocaoProdutoRequest {
	@NotNull(message = "Campo Promocao não pode está vazio.")
	private Integer promocao; 
}
