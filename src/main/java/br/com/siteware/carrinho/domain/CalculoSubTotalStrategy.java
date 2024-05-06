package br.com.siteware.carrinho.domain;

public interface CalculoSubTotalStrategy {
    Double calcularSubTotal(Double preco, int quantidade);
}
