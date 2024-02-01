package br.com.siteware.cliente.application.service;

import java.util.UUID;

import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import br.com.siteware.cliente.domain.Cliente;

public interface ClienteService {

	ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest);

	ClienteDetalhadoResponse buscaClientePorId(String email, UUID idCliente);

	Cliente detalhaClientePorId(UUID idCliente);
	
	Cliente detalhaClientePorEmail(String emailCliente);

}
