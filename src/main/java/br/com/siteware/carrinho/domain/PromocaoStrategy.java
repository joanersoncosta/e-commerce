package br.com.siteware.carrinho.domain;

public interface PromocaoStrategy {
    Double calcularSubTotal(Double preco, int quantidade);
}
