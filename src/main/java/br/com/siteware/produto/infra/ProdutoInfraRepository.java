package br.com.siteware.produto.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProdutoInfraRepository implements ProdutoRepository {
	private final ProdutoSpringMongoDbRepository produtoSpringMongoDbRepository;

	@Override
	public Produto salva(Produto produto) {
		log.info("[start] ProdutoInfraRepository - salva");
		try {
			produtoSpringMongoDbRepository.save(produto);
		}catch (DataIntegrityViolationException ex) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Produto já cadastrado.");
		}
		log.info("[finish] ProdutoInfraRepository - salva");
		return produto;
	}

}
