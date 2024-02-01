package br.com.siteware.cliente.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ClienteInfraRepository implements ClienteRepository {
	private final ClienteSpringDBMongoRepository clienteSpringDBMongoRepository;

	@Override
	public Cliente salva(Cliente cliente) {
		log.info("[start] ClienteInfraRepository - salva");
		try {
			clienteSpringDBMongoRepository.save(cliente);
		}catch (DataIntegrityViolationException ex) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Cliente já cadastrado.");
		}
		log.info("[finish] ClienteInfraRepository - salva");
		return cliente;
	}

}
