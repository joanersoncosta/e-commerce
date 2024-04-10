package br.com.siteware.pedido.application.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public interface PedidoAPI {

	@GetMapping(path = "/cliente/{idCliente}/detalha")
	@ResponseStatus(value = HttpStatus.CREATED)
	PedidoDetalhadoResponse detalhaPedido(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idCliente") UUID idCliente);

}
