package br.com.siteware.pedido.application.api;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.pedido.application.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PedidoRestController implements PedidoAPI {
	private final PedidoService pedidoService;

	@Override
	public PedidoDetalhadoResponse detalhaPedido(String email) {
		log.info("[inicia] PedidoRestController - buscaPedido");
		PedidoDetalhadoResponse pedidoResponse = pedidoService.buscaPedido(email);
		log.info("[finaliza] PedidoRestController - buscaPedido");
		return pedidoResponse;
	}

}
