package br.com.siteware.carrinho.application.api;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CarrinhoRequest {
	@NotNull(message = "Campo idProduto não pode estar vazio")
	@Schema(description = "Este é o ID do Produto")
	private UUID idProduto;
	@NotNull(message = "Campo quantidade não pode ser nulo.")
	@Schema(description = "Quantidade de Produtos no Carrinho", example = "12")
	private Integer quantidade;

}
