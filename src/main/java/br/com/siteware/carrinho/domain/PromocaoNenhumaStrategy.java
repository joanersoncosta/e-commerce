package br.com.siteware.carrinho.domain;

public class PromocaoNenhumaStrategy implements PromocaoStrategy {

	@Override
	public Double calcularSubTotal(Double preco, int quantidade) {
		return preco * quantidade;
	}
}
