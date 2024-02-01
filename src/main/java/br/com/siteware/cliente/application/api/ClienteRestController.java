package br.com.siteware.cliente.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.cliente.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ClienteRestController implements ClienteAPI {
	private final ClienteService clienteService;
	
	@Override
	public ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest) {
		log.info("[inicia] ClienteRestController - criaNovoCliente");
		ClienteIdResponse idCliente = clienteService.criaNovoCliente(clienteRequest);
		log.info("[finaliza] ClienteRestController - criaNovoCliente");
		return idCliente;
	}

	@Override
	public ClienteDetalhadoResponse buscaClientePorId(String email, UUID idCliente) {
		log.info("[inicia] ClienteRestController - buscaClientePorId");
		log.info("[idCliente] {}", idCliente);
		ClienteDetalhadoResponse clienteDetalhado = clienteService.buscaClientePorId(email, idCliente);		
		log.info("[finaliza] ClienteRestController - buscaClientePorId");		
		return clienteDetalhado;
	}

	@Override
	public List<ClienteListResponse> buscaTodosOsClientes(String email) {
		log.info("[inicia] ClienteRestController - buscaTodosOsClientes");
		List<ClienteListResponse> clientes = clienteService.buscaTodosOsClientes(email);		
		log.info("[finaliza] ClienteRestController - buscaTodosOsClientes");		
		return clientes;
	}
}
