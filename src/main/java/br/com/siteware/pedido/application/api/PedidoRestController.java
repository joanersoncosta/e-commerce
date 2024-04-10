package br.com.siteware.pedido.application.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.config.security.service.TokenService;
import br.com.siteware.handler.APIException;
import br.com.siteware.pedido.application.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PedidoRestController implements PedidoAPI {
	private final PedidoService pedidoService;
	private final TokenService tokenService;

	@Override
	public PedidoDetalhadoResponse detalhaPedido(String token, UUID idCliente) {
		log.info("[inicia] PedidoRestController - buscaPedido");
		var usuarilEmail = getUsuarioByToken(token);
		PedidoDetalhadoResponse pedidoResponse = pedidoService.buscaPedido(usuarilEmail, idCliente);
		log.info("[finaliza] PedidoRestController - buscaPedido");
		return pedidoResponse;
	}
	
	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token).orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}

}
