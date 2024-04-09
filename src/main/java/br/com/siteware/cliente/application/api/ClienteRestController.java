package br.com.siteware.cliente.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.cliente.application.service.ClienteService;
import br.com.siteware.config.security.service.TokenService;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ClienteRestController implements ClienteAPI {
	private final ClienteService clienteService;
	private final TokenService tokenService;
	
	@Override
	public ClienteIdResponse criaNovoCliente(ClienteNovoRequest clienteRequest) {
		log.info("[inicia] ClienteRestController - criaNovoCliente");
		ClienteIdResponse idCliente = clienteService.criaNovoCliente(clienteRequest);
		log.info("[finaliza] ClienteRestController - criaNovoCliente");
		return idCliente;
	}

	@Override
	public ClienteDetalhadoResponse buscaClientePorId(String token, UUID idCliente) {
		log.info("[inicia] ClienteRestController - buscaClientePorId");
		log.info("[idCliente] {}", idCliente);
		String email = getUsuarioByToken(token);
		ClienteDetalhadoResponse clienteDetalhado = clienteService.buscaClientePorId(email, idCliente);		
		log.info("[finaliza] ClienteRestController - buscaClientePorId");		
		return clienteDetalhado;
	}

	@Override
	public List<ClienteListResponse> buscaTodosOsClientes(String token) {
		log.info("[inicia] ClienteRestController - buscaTodosOsClientes");
		String email = getUsuarioByToken(token);
		List<ClienteListResponse> clientes = clienteService.buscaTodosOsClientes(email);		
		log.info("[finaliza] ClienteRestController - buscaTodosOsClientes");		
		return clientes;
	}
	
	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token).orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}
}
