package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.produto.application.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ProdutoRestController implements ProdutoAPI {
	private final ProdutoService produtoService;

	@Override
	public ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		ProdutoIdResponse produtoIdResponse = produtoService.cadastraProduto(email, produtoRequest);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoIdResponse;
	}

	@Override
	public ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		ProdutoDetalhadoResponse produtoDetalhadoResponse = produtoService.buscaProdutoPorId(idProduto);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoDetalhadoResponse;

	}

	@Override
	public List<ProdutoListResponse> buscaTodosOsProdutos() {
		log.info("[inicia] ProdutoRestController - buscaTodosOsProdutos");
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaTodosOsProdutos");
		return produtoListResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorCategoria(String categoria) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorCategoria");
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutosPorCategoria(categoria);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorCategoria");
		return produtoListResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorNome(String nomeProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorNome");
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutosPorNome(nomeProduto);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorNome");
		return produtoListResponse;
	}

	@Override
	public void deletaProdutoPorId(UUID idProduto) {
		log.info("[inicia] ProdutoRestController - deletaProdutoPorId");
		produtoService.deletaProdutoPorId(idProduto);
		log.info("[finaliza] ProdutoRestController - deletaProdutoPorId");
	}

	@Override
	public void editaProdutoPorId(String email, UUID idProduto, EditaProdutoRequest editaProduto) {
		log.info("[inicia] ProdutoRestController - editaProdutoPorId");
		produtoService.editaProdutoPorId(email, idProduto, editaProduto);
		log.info("[finaliza] ProdutoRestController - editaProdutoPorId");
	}

	@Override
	public void alteraPromocaoDoProdutoPorId(String email, UUID idProduto,
			AlteraPromocaoProdutoRequest editaPromocaoProduto) {
		log.info("[inicia] ProdutoRestController - alteraPromocaoDoProdutoPorId");
		produtoService.alteraPromocaoDoProdutoPorId(email, idProduto, editaPromocaoProduto);
		log.info("[finaliza] ProdutoRestController - alteraPromocaoDoProdutoPorId");

	}

	@Override
	public List<ProdutoListResponse> buscaProdutoComPromocao() {
		log.info("[inicia] ProdutoRestController - buscaProdutoComPromocao");
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutoComPromocao();
		log.info("[finaliza] ProdutoRestController - buscaProdutoComPromocao");
		return produtoListResponse;
	}

	@Override
	public void aplicaPromocaoAoProduto(String email, UUID idProduto, PromocaoProdutoRequest promocaoRequest) {
		log.info("[inicia] ProdutoRestController - aplicaPromocaoAoProduto");
		produtoService.aplicaPromocaoAoProduto(email, idProduto, promocaoRequest);
		log.info("[finaliza] ProdutoRestController - aplicaPromocaoAoProduto");
	}

	@Override
	public void encerraPromocaoDoProduto(String email, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - encerraPromocaoDoProduto");
		produtoService.encerraPromocaoDoProduto(email, idProduto);
		log.info("[finaliza] ProdutoRestController - encerraPromocaoDoProduto");
	}

}
