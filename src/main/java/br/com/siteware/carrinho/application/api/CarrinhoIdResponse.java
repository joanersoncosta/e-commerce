package br.com.siteware.carrinho.application.api;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarrinhoIdResponse {
	@Schema(description = "Este é o ID do Carrinho")
	private UUID idCarrinho;
}
