package br.com.siteware.carrinho.infra;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.siteware.carrinho.domain.Carrinho;

public interface CarrinhoSpringMongoDbRepository extends MongoRepository<Carrinho, UUID>{

}
