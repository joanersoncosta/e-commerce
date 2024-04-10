package br.com.siteware.pedido.application.service;

import java.util.UUID;

import br.com.siteware.pedido.application.api.PedidoDetalhadoResponse;

public interface PedidoService {

	PedidoDetalhadoResponse buscaPedido(String email, UUID idCliente);

}
