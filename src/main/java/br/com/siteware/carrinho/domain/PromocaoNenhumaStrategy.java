package br.com.siteware.carrinho.domain;

public class PromocaoNenhumaStrategy implements CalculoSubTotalStrategy {

	@Override
	public Double calcularSubTotal(Double preco, int quantidade) {
		return preco * quantidade;
	}
}
