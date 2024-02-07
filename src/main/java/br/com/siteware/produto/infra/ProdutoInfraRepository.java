package br.com.siteware.produto.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProdutoInfraRepository implements ProdutoRepository {
	private final ProdutoSpringMongoDbRepository produtoSpringMongoDbRepository;
	private final MongoTemplate mongoTemplate;
	
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

	@Override
	public Optional<Produto> detalhaProdutoPorId(UUID idProduto) {
		log.info("[start] ProdutoInfraRepository - detalhaProdutoPorId");
		Optional<Produto> produto = produtoSpringMongoDbRepository.findById(idProduto);
		log.info("[finish] ProdutoInfraRepository - detalhaProdutoPorId");
		return produto;
	}

	@Override
	public List<Produto> buscaTodosOsProdutos() {
		log.info("[start] ProdutoInfraRepository - buscaTodosProdutos");
		List<Produto> produtos = produtoSpringMongoDbRepository.findAll();
		log.info("[finish] ProdutoInfraRepository - buscaTodosProdutos");
		return produtos;
	}

	@Override
	public List<Produto> buscaProdutosPorCategoria(Categoria categoriaVarida) {
		log.info("[start] ProdutoInfraRepository - buscaTodosProdutos");
		Query query = new Query();
		query.addCriteria(Criteria.where("categoria").is(categoriaVarida));
		List<Produto> produtos = mongoTemplate.find(query, Produto.class);
		log.info("[finish] ProdutoInfraRepository - buscaTodosProdutos");
		return produtos;
	}

	@Override
	public void deletaProduto(Produto produto) {
		log.info("[start] ProdutoInfraRepository - deletaProduto");
		produtoSpringMongoDbRepository.delete(produto);
		log.info("[finish] ProdutoInfraRepository - deletaProduto");
	}

	@Override
	public void editaProduto(Produto produto, EditaProdutoRequest request) {
		log.info("[start] ProdutoInfraRepository - editaProduto");
		Query query = new Query();
		query.addCriteria(Criteria.where("idProduto").is(produto.getIdProduto()));
		
		Update update = new Update();
		update.set("nome", request.getNome())
			.set("descricao", request.getDescricao())
			.set("preco", request.getPreco());
		
		mongoTemplate.updateFirst(query, update,Produto.class);
		log.info("[finish] ProdutoInfraRepository - editaProduto");
	}

	@Override
	public void alteraPromocaoDoProduto(Produto produto, PromocaoProduto promocao) {
		log.info("[start] ProdutoInfraRepository - alteraPromocaoDoProduto");
		Query query = new Query();
		query.addCriteria(Criteria.where("idProduto").is(produto.getIdProduto()));
		
		Update update = new Update();
		update.set("promocao", promocao);
		update.set("statusPromocao", produto.getStatusPromocao());
		
		mongoTemplate.updateFirst(query, update, Produto.class);
		log.info("[finish] ProdutoInfraRepository - alteraPromocaoDoProduto");
	}

	@Override
	public List<Produto> buscaProdutoComPromocao() {
		log.info("[start] ProdutoInfraRepository - alteraPromocaoDoProduto");
		Query query = new Query();
		query.addCriteria(Criteria.where("statusPromocao").is(PromocaoProdutoStatus.ATIVO));
		
		List<Produto> produtos = mongoTemplate.find(query, Produto.class);
		log.info("[finish] ProdutoInfraRepository - alteraPromocaoDoProduto");
		return produtos;
	}

}
