package br.com.siteware.carrinho.application.service;

import java.util.List;

import br.com.siteware.carrinho.application.api.CarrinhoIdResponse;
import br.com.siteware.carrinho.application.api.CarrinhoListResponse;
import br.com.siteware.carrinho.application.api.CarrinhoRequest;

public interface CarrinhoService {

	CarrinhoIdResponse adicionaProdutoAoCarrinho(String email, CarrinhoRequest carrinhoRequest);

	List<CarrinhoListResponse> listaCarrinhoDoCliente(String email);

}