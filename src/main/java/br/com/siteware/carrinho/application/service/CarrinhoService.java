package br.com.siteware.carrinho.application.service;

import br.com.siteware.carrinho.application.api.CarrinhoIdResponse;
import br.com.siteware.carrinho.application.api.CarrinhoRequest;

public interface CarrinhoService {

	CarrinhoIdResponse adicionaProdutoAoCarrinho(String email, CarrinhoRequest carrinhoRequest);

}
