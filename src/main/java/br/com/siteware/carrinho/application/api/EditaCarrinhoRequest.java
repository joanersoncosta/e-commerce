package br.com.siteware.carrinho.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class EditaCarrinhoRequest {
	@NotNull(message = "Campo quantidade não pode ser nulo.")
	private Integer quantidade;
}
