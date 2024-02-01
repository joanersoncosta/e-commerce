package br.com.siteware.cliente.application.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public/v1/cliente")
public interface ClienteAPI {

	@PostMapping(path = "/login")
	@ResponseStatus(code = HttpStatus.CREATED)
	ClienteIdResponse criaNovoCliente(@RequestBody @Valid ClienteNovoRequest clienteRequest);

	@GetMapping(value = "/{idCliente}/busca-cliente")
	@ResponseStatus(value = HttpStatus.OK)
	ClienteDetalhadoResponse buscaClientePorId(@PathVariable(value = "idCliente") UUID idCliente);

}
