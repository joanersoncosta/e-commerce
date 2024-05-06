package br.com.siteware.carrinho.domain.strategy;

public interface PromocaoStrategy {
    Double calcularSubTotal(Double preco, int quantidade);
}
