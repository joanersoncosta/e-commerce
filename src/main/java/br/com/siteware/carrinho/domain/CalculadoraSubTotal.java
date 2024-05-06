package br.com.siteware.carrinho.domain;

import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculadoraSubTotal {
	private PromocaoProduto promocao;
	private PromocaoStrategyMapper promocaoStrategyMapper;

	public CalculadoraSubTotal(PromocaoProduto promocao) {
		this.promocao = promocao;
		this.promocaoStrategyMapper = new PromocaoStrategyMapper();
	}

	public Double calcularSubTotal(Double preco, int quantidade) {
		PromocaoStrategy promocaoStrategy = promocaoStrategyMapper.getStrategy(this.promocao);
		return promocaoStrategy.calcularSubTotal(preco, quantidade);
	}
}
