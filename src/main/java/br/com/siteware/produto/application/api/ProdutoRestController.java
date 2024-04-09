package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.config.security.service.TokenService;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ProdutoRestController implements ProdutoAPI {
	private final ProdutoService produtoService;
	private final TokenService tokenService;

	@Override
	public ProdutoIdResponse cadastraProduto(String token, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		String email = getUsuarioByToken(token);
		ProdutoIdResponse produtoIdResponse = produtoService.cadastraProduto(email, produtoRequest);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoIdResponse;
	}

	@Override
	public ProdutoDetalhadoResponse buscaProdutoPorId(String token, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - cadastraProduto");
		getUsuarioByToken(token);
		ProdutoDetalhadoResponse produtoDetalhadoResponse = produtoService.buscaProdutoPorId(idProduto);
		log.info("[finaliza] ProdutoRestController - cadastraProduto");
		return produtoDetalhadoResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaTodosOsProdutos(String token) {
		log.info("[inicia] ProdutoRestController - buscaTodosOsProdutos");
		getUsuarioByToken(token);
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaTodosOsProdutos");
		return produtoListResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorCategoria(String token, String categoria) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorCategoria");
		getUsuarioByToken(token);
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutosPorCategoria(categoria);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorCategoria");
		return produtoListResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorNome(String token, String nomeProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorNome");
		getUsuarioByToken(token);
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutosPorNome(nomeProduto);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorNome");
		return produtoListResponse;
	}

	@Override
	public void deletaProdutoPorId(String token, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - deletaProdutoPorId");
		String email = getUsuarioByToken(token);
		produtoService.deletaProdutoPorId(email, idProduto);
		log.info("[finaliza] ProdutoRestController - deletaProdutoPorId");
	}

	@Override
	public void editaProdutoPorId(String token, UUID idProduto, EditaProdutoRequest editaProduto) {
		log.info("[inicia] ProdutoRestController - editaProdutoPorId");
		String email = getUsuarioByToken(token);
		produtoService.editaProdutoPorId(email, idProduto, editaProduto);
		log.info("[finaliza] ProdutoRestController - editaProdutoPorId");
	}

	@Override
	public void alteraPromocaoDoProdutoPorId(String token, UUID idProduto,
			AlteraPromocaoProdutoRequest editaPromocaoProduto) {
		log.info("[inicia] ProdutoRestController - alteraPromocaoDoProdutoPorId");
		String email = getUsuarioByToken(token);
		produtoService.alteraPromocaoDoProdutoPorId(email, idProduto, editaPromocaoProduto);
		log.info("[finaliza] ProdutoRestController - alteraPromocaoDoProdutoPorId");

	}

	@Override
	public List<ProdutoListResponse> buscaProdutoComPromocao(String token) {
		log.info("[inicia] ProdutoRestController - buscaProdutoComPromocao");
		getUsuarioByToken(token);
		List<ProdutoListResponse> produtoListResponse = produtoService.buscaProdutoComPromocao();
		log.info("[finaliza] ProdutoRestController - buscaProdutoComPromocao");
		return produtoListResponse;
	}

	@Override
	public void aplicaPromocaoAoProduto(String token, UUID idProduto, Integer percentualDesconto) {
		log.info("[inicia] ProdutoRestController - aplicaPromocaoAoProduto");
		String email = getUsuarioByToken(token);
		produtoService.aplicaPromocaoAoProduto(email, idProduto, percentualDesconto);
		log.info("[finaliza] ProdutoRestController - aplicaPromocaoAoProduto");
	}

	@Override
	public void encerraPromocaoDoProduto(String token, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - encerraPromocaoDoProduto");
		String email = getUsuarioByToken(token);
		produtoService.encerraPromocaoDoProduto(email, idProduto);
		log.info("[finaliza] ProdutoRestController - encerraPromocaoDoProduto");
	}
	
	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token).orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}


}
