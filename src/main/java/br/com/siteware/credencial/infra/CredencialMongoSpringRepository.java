package br.com.siteware.credencial.infra;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.siteware.credencial.domain.Credencial;

public interface CredencialMongoSpringRepository extends MongoRepository<Credencial, String> {
	Optional<Credencial> findByUsuario(String Usuario);
}
