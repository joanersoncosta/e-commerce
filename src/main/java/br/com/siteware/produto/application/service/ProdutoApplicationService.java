package br.com.siteware.produto.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.credencial.application.service.CredencialService;
import br.com.siteware.credencial.domain.Credencial;
import br.com.siteware.handler.APIException;
import br.com.siteware.produto.application.api.AlteraPromocaoProdutoRequest;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.api.ProdutoDetalhadoResponse;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoListResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProdutoApplicationService implements ProdutoService {
	private final ProdutoRepository produtoRepository;
	private final CredencialService credencialService;

	@Override
	public ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoApplicationService - cadastraProduto");
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		Produto produto = produtoRepository.salva(new Produto(credencialUsuario.getUsername(), produtoRequest));
		log.info("[finaliza] ProdutoApplicationService - cadastraProduto");
		return ProdutoIdResponse.builder().idProduto(produto.getIdProduto()).build();
	}

	@Override
	public ProdutoDetalhadoResponse buscaProdutoPorId(UUID idProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutoPorId");
		log.info("[idProduto] {}", idProduto);
		var produtoResponse = produtoRepository.detalhaProdutoPorId(idProduto)
				.map(ProdutoDetalhadoResponse::converte)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		log.info("[finaliza] ProdutoRestController - buscaProdutoPorId");
		return produtoResponse;
	}

	@Override
	public List<ProdutoListResponse> buscaTodosOsProdutos() {
		log.info("[inicia] ProdutoRestController - buscaTodosProdutos");
		List<Produto> produtos = produtoRepository.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaTodosProdutos");
		return ProdutoListResponse.converte(produtos);
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorCategoria(String categoria) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorCategoria");
		Categoria categoriaValida = Categoria.validaCategoria(categoria.toUpperCase())
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Nenhum Produto encontrado para esta categoria."));
		List<Produto> produtos = produtoRepository.buscaProdutosPorCategoria(categoriaValida);
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorCategoria");
		return ProdutoListResponse.converte(produtos);
	}

	@Override
	public List<ProdutoListResponse> buscaProdutosPorNome(String nomeProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutosPorNome");
		List<Produto> produtos = produtoRepository.buscaTodosOsProdutos();
		log.info("[finaliza] ProdutoRestController - buscaProdutosPorNome");
		return ProdutoListResponse.converte(produtos, nomeProduto);
	}

	@Override
	public void deletaProdutoPorId(String email, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - buscaProdutoPorId");
		log.info("[idProduto] {}", idProduto);
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		Produto produto = produtoRepository.detalhaProdutoPorId(idProduto)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.pertenceAoUsuario(credencialUsuario.getUsername());
		produtoRepository.deletaProduto(produto);
		log.info("[finaliza] ProdutoRestController - buscaProdutoPorId");
	}

	@Override
	public void editaProdutoPorId(String email, UUID idProduto, EditaProdutoRequest editaProduto) {
		log.info("[inicia] ProdutoRestController - editaProdutoPorId");
		log.info("[idProduto] {}", idProduto);
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		Produto produto = produtoRepository.detalhaProdutoPorId(idProduto)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.pertenceAoUsuario(credencialUsuario.getUsername());
		produtoRepository.editaProduto(produto, editaProduto);
		log.info("[finaliza] ProdutoRestController - editaProdutoPorId");
	}

	@Override
	public void alteraPromocaoDoProdutoPorId(String email, UUID idProduto,
			AlteraPromocaoProdutoRequest request) {
		log.info("[inicia] ProdutoRestController - editaProdutoPorId");
		log.info("[idProduto] {}", idProduto);
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		PromocaoProduto promocao = PromocaoProduto.validaPromocao(request.getPromocao())
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Promoção invalida."));

		Produto produto = produtoRepository.detalhaProdutoPorId(idProduto)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.pertenceAoUsuario(credencialUsuario.getUsername());
		produto.alteraStatusPromocao(promocao);
		produtoRepository.alteraPromocaoDoProduto(produto, promocao);
		log.info("[finaliza] ProdutoRestController - editaProdutoPorId");
	}

	@Override
	public List<ProdutoListResponse> buscaProdutoComPromocao() {
		log.info("[inicia] ProdutoRestController - buscaProdutoComPromocao");
		List<Produto> produtos = produtoRepository.buscaProdutoComPromocao();
		log.info("[finaliza] ProdutoRestController - buscaProdutoComPromocao");
		return ProdutoListResponse.converte(produtos);
	}

	@Override
	public void aplicaPromocaoAoProduto(String email, UUID idProduto, Integer percentualDesconto) {
		log.info("[inicia] ProdutoRestController - aplicaPromocaoAoProduto");
		log.info("[idProduto] {}", idProduto);
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		Produto produto = produtoRepository.detalhaProdutoPorId(idProduto)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.pertenceAoUsuario(credencialUsuario.getUsername());
		produtoRepository.aplicaPromocaoAoProduto(produto, percentualDesconto);
		log.info("[finaliza] ProdutoRestController - aplicaPromocaoAoProduto");
	}

	@Override
	public void encerraPromocaoDoProduto(String email, UUID idProduto) {
		log.info("[inicia] ProdutoRestController - encerraPromocaoDoProduto");
		log.info("[idProduto] {}", idProduto);
		Credencial credencialUsuario = credencialService.buscaCredencialPorUsuario(email);
		credencialUsuario.validaAcessoAdmin();
		Produto produto = produtoRepository.detalhaProdutoPorId(idProduto)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Produto não encontrado."));
		produto.pertenceAoUsuario(credencialUsuario.getUsername());
		produto.validaPromocao();
		produtoRepository.encerraPromocaoDoProduto(produto);
		log.info("[finaliza] ProdutoRestController - encerraPromocaoDoProduto");
	}

}