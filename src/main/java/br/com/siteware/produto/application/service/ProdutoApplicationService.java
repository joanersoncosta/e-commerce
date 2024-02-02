package br.com.siteware.produto.application.service;

import org.springframework.stereotype.Service;

import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.produto.application.api.ProdutoIdResponse;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.repository.ProdutoRepository;
import br.com.siteware.produto.domain.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProdutoApplicationService implements ProdutoService {
	private final ProdutoRepository produtoRepository;
	private final ClienteRepository clienteRepository;
	
	@Override
	public ProdutoIdResponse cadastraProduto(String email, ProdutoRequest produtoRequest) {
		log.info("[inicia] ProdutoApplicationService - cadastraProduto");
		clienteRepository.detalhaClientePorEmail(email);
		Produto produto = produtoRepository.salva(new Produto(produtoRequest));
		log.info("[finaliza] ProdutoApplicationService - cadastraProduto");
		return ProdutoIdResponse.builder().idProduto(produto.getIdProduto()).build();
	}

}
