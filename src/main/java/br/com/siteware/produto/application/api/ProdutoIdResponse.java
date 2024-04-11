package br.com.siteware.produto.application.api;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProdutoIdResponse {
	@Schema(description = "Este é o ID do produto")
	private UUID idProduto;
}
