package br.com.siteware.cliente.application.service;

import java.util.UUID;

import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;

public interface ClienteService {

	ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest);

	ClienteDetalhadoResponse buscaClientePorId(UUID idCliente);

}
