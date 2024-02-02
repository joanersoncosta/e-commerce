package br.com.siteware.produto.infra;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.siteware.produto.domain.Produto;

public interface ProdutoSpringMongoDbRepository extends MongoRepository<Produto, UUID>{

}
