package br.com.siteware.carrinho.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarrinhoIdResponse {
	private UUID idCarrinho;
}
