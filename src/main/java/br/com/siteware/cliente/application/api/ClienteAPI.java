package br.com.siteware.cliente.application.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cliente")
public interface ClienteAPI {

	@PostMapping(path = "/public/cadastro")
	@ResponseStatus(code = HttpStatus.CREATED)
	ClienteIdResponse criaNovoCliente(@RequestBody @Valid ClienteNovoRequest clienteRequest);

	@GetMapping(value = "/public/{idCliente}/busca-cliente")
	@ResponseStatus(value = HttpStatus.OK)
	ClienteDetalhadoResponse buscaClientePorId(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idCliente") UUID idCliente);

	@GetMapping(value = "/admin/busca-clientes")
	@ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
	List<ClienteListResponse> buscaTodosOsClientes(@RequestHeader(name = "Authorization", required = true) String token);

}
