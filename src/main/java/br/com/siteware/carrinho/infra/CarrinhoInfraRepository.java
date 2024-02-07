package br.com.siteware.carrinho.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CarrinhoInfraRepository implements CarrinhoRepository {
	private final CarrinhoSpringMongoDbRepository carrinhoSpringMongoDbRepository;
	private final MongoTemplate mongoTemplate;

	@Override
	public Carrinho salva(Carrinho carrinho) {
		log.info("[start] CarrinhoInfraRepository - salva");
		carrinhoSpringMongoDbRepository.save(carrinho);
		log.info("[finish] CarrinhoInfraRepository - salva");
		return carrinho;
	}

	@Override
	public List<Carrinho> listaCarrinhoDoCliente(UUID idCliente) {
		log.info("[start] CarrinhoInfraRepository - salva");
		Query query = new Query();
		query.addCriteria(Criteria.where("idCliente").is(idCliente));
		List<Carrinho> carrinho = mongoTemplate.find(query, Carrinho.class);
		log.info("[finish] CarrinhoInfraRepository - salva");
		return carrinho;
	}

	@Override
	public Carrinho buscaCarrinhoPorId(UUID idCarrinho) {
		log.info("[start] CarrinhoInfraRepository - salva");
		Carrinho carrinho  = carrinhoSpringMongoDbRepository.findById(idCarrinho)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Carrinho não encontrado."));
		log.info("[finish] CarrinhoInfraRepository - salva");
		return carrinho;
	}

	@Override
	public void removeCarrinho(Carrinho carrinho) {
		log.info("[start] CarrinhoInfraRepository - removeCarrinho");
		Query query = new Query();
		query.addCriteria(Criteria.where("idCarrinho").is(carrinho.getIdCarrinho()));
		mongoTemplate.remove(query, Carrinho.class);
		log.info("[finish] CarrinhoInfraRepository - removeCarrinho");
	}

	@Override
	public void atualizaCarrinho(Carrinho carrinho) {
		log.info("[start] CarrinhoInfraRepository - removeCarrinho");
		carrinhoSpringMongoDbRepository.save(carrinho);
		log.info("[finish] CarrinhoInfraRepository - removeCarrinho");
	}

}
