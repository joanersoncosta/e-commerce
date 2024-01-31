package br.com.siteware.cliente.infra;

import org.springframework.stereotype.Repository;

import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
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
		clienteSpringDBMongoRepository.save(cliente);
		log.info("[finish] ClienteInfraRepository - salva");
		return cliente;
	}

}
