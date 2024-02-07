package br.com.siteware.pedido.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PedidoDetalhadoResponse {
	private UUID idPedido;
}
