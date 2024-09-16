package br.com.siteware.cliente.infra;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.siteware.cliente.domain.Cliente;

public interface ClienteSpringDBMongoRepository extends MongoRepository<Cliente, UUID>{

	Optional<Cliente> findByEmail(String emailCliente);

}
