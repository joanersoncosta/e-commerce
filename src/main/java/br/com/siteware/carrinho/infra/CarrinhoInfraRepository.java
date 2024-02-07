package br.com.siteware.carrinho.infra;

import org.springframework.stereotype.Repository;

import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CarrinhoInfraRepository implements CarrinhoRepository {
	private final CarrinhoSpringMongoDbRepository carrinhoSpringMongoDbRepository;

	@Override
	public Carrinho salva(Carrinho carrinho) {
		log.info("[start] CarrinhoInfraRepository - salva");
		carrinhoSpringMongoDbRepository.save(carrinho);
		log.info("[finish] CarrinhoInfraRepository - salva");
		return carrinho;
	}

}
