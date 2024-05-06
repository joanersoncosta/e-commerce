package br.com.siteware.carrinho.domain;

public class PromocaoLeve3Pague10ReaisStrategy implements CalculoSubTotalStrategy{

    @Override
    public Double calcularSubTotal(Double preco, int quantidade) {
        if (quantidade == 3) {
            return 10.00;
        } else {
            return preco * quantidade;
        }
    }
}
