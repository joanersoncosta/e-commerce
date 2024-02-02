package br.com.siteware.produto.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProdutoIdResponse {
	private UUID idProduto;
}
