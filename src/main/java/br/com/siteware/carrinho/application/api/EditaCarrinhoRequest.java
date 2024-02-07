package br.com.siteware.carrinho.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EditaCarrinhoRequest {
	@NotNull(message = "Campo quantidade não pode ser nulo.")
	private Integer quantidade;
}
