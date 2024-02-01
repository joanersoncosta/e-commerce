package br.com.siteware.cliente.application.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClienteApplicationService implements ClienteService {
	private final ClienteRepository clienteRepository;

	@Override
	public ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest) {
		log.info("[inicia] ClienteApplicationService - criaNovoCliente");
		Cliente cliente = clienteRepository.salva(new Cliente(clienteRequest));
		log.info("[finaliza] ClienteApplicationService - criaNovoCliente");
		return ClienteIdResponse.builder().idCliente(cliente.getIdCliente()).build();
	}

	@Override
	public ClienteDetalhadoResponse buscaClientePorId(UUID idCliente) {
		log.info("[inicia] ClienteApplicationService - buscaClientePorId");
		log.info("[idCliente] {}", idCliente);
		var clienteResponse = clienteRepository.detalhaClientePorId(idCliente)
				.map(ClienteDetalhadoResponse::converteClienteParaResponse)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
		log.info("[finaliza] ClienteApplicationService - buscaClientePorId");
		return clienteResponse;

	}

}
