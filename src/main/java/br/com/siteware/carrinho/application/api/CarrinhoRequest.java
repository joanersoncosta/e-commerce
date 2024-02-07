package br.com.siteware.carrinho.application.api;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CarrinhoRequest {
	@NotNull(message = "Campo idProduto não pode estar vazio")
	private UUID idProduto;
	@NotNull(message = "Campo quantidade não pode ser nulo.")
	private Integer quantidade;

}
