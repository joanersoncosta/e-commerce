package br.com.siteware.carrinho.domain.strategy;

import java.util.HashMap;
import java.util.Map;

import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PromocaoStrategyMapper {
    private Map<PromocaoProduto, PromocaoStrategy> promocaoStrategyMapper = new HashMap<>();

    public PromocaoStrategyMapper() {
    	promocaoStrategyMapper.put(PromocaoProduto.LEVE_2_PAGUE_1, new PromocaoLeve2Pague1Strategy());
    	promocaoStrategyMapper.put(PromocaoProduto.LEVE_3_PAGUE_10_REAIS, new PromocaoLeve3Pague10ReaisStrategy());
    	promocaoStrategyMapper.put(PromocaoProduto.NENHUM, new PromocaoNenhumaStrategy());
    }
    
    public PromocaoStrategy getStrategy(PromocaoProduto promocaoProduto) {
        PromocaoStrategy strategy = promocaoStrategyMapper.get(promocaoProduto);
        return strategy;
    }
}
