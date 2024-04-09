package br.com.siteware.cliente.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.cliente.application.api.ClienteIdResponse;
import br.com.siteware.cliente.application.api.ClienteListResponse;
import br.com.siteware.cliente.application.api.ClienteNovoRequest;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.credencial.application.service.CredencialService;
import br.com.siteware.credencial.domain.Credencial;
import br.com.siteware.credencial.domain.CredencialCliente;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClienteApplicationService implements ClienteService {
	private final ClienteRepository clienteRepository;
	private final CredencialService credencialService;

	@Override
	public ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest) {
		log.info("[inicia] ClienteApplicationService - criaNovoCliente");
		Cliente cliente = clienteRepository.salva(new Cliente(clienteRequest));
		credencialService.criaNovaCredencial(new CredencialCliente(clienteRequest));
		log.info("[finaliza] ClienteApplicationService - criaNovoCliente");
		return ClienteIdResponse.builder().idCliente(cliente.getIdCliente()).build();
	}

	@Override
	public ClienteDetalhadoResponse buscaClientePorId(String email, UUID idCliente) {
		log.info("[inicia] ClienteApplicationService - buscaClientePorId");
		Cliente emailCliente = clienteRepository.detalhaClientePorEmail(email);
		log.info("[emailCliente] {}", emailCliente);
		log.info("[idCliente] {}", idCliente);
		Cliente cliente = clienteRepository.detalhaClientePorId(idCliente)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
		log.info("[finaliza] ClienteApplicationService - buscaClientePorId");
		return ClienteDetalhadoResponse.converteClienteParaResponse(cliente);
	}

	@Override
	public Cliente detalhaClientePorId(UUID idCliente) {
		log.info("[inicia] ClienteApplicationService - buscaClientePorId");
		log.info("[idCliente] {}", idCliente);
		var cliente = clienteRepository.detalhaClientePorId(idCliente)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
		log.info("[finaliza] ClienteApplicationService - buscaClientePorId");
		return cliente;
	}

	@Override
	public Cliente detalhaClientePorEmail(String emailCliente) {
		log.info("[inicia] ClienteApplicationService - buscaClientePorId");
		log.info("[emailCliente] {}", emailCliente);
		var cliente = clienteRepository.detalhaClientePorEmail(emailCliente);
		log.info("[finaliza] ClienteApplicationService - buscaClientePorId");
		return cliente;
	}

	@Override
	public List<ClienteListResponse> buscaTodosOsClientes(String email) {
		log.info("[inicia] ClienteApplicationService - buscaTodosOsClientes");
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAdmin();
		List<Cliente> clientes = clienteRepository.buscaClientes();		
		log.info("[finaliza] ClienteApplicationService - buscaTodosOsClientes");
		return ClienteListResponse.converte(clientes);
	}

}
