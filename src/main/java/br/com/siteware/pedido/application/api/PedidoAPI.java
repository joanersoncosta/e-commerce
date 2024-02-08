package br.com.siteware.pedido.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public interface PedidoAPI {

	@GetMapping(path = "/detalha")
	@ResponseStatus(value = HttpStatus.CREATED)
	PedidoDetalhadoResponse detalhaPedido(@RequestParam(value = "email", required = true) String email);

}
