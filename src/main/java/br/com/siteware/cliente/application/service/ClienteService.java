package br.com.siteware.cliente.application.service;

import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;

public interface ClienteService {

	ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest);

}
