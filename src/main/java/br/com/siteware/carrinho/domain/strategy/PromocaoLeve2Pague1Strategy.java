package br.com.siteware.carrinho.domain.strategy;

public class PromocaoLeve2Pague1Strategy implements PromocaoStrategy {
	
	@Override
	public Double calcularSubTotal(Double preco, int quantidade) {
		if (quantidade == 2) {
			return preco;
		} else {
			return preco * quantidade;
		}
	}
}
