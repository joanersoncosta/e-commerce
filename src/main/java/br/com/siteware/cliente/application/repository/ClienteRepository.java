package br.com.siteware.cliente.application.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.siteware.cliente.domain.Cliente;

public interface ClienteRepository {

	Cliente salva(Cliente cliente);

	Optional<Cliente> detalhaClientePorId(UUID idCliente);

	Cliente detalhaClientePorEmail(String emailCliente);

}
