package br.com.siteware.produto.domain.enuns;

import java.util.Arrays;
import java.util.Optional;

public enum PromocaoProduto {
	NENHUM(1), LEVE_2_PAGUE_1(2), LEVE_3_PAGUE_10_REAIS(3);

	private Integer promocao;

	private PromocaoProduto(Integer promocao) {
		this.promocao = promocao;
	}

	public Integer getPromocao() {
		return promocao;
	}

	public static Optional<PromocaoProduto> validaPromocao(Integer promocao) {
		return Arrays.stream(values()).filter(valorCorrespondente -> valorCorrespondente.getPromocao().equals(promocao))
				.findFirst();
	}

}
