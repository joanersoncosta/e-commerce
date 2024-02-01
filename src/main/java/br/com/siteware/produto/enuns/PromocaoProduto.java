package br.com.siteware.produto.enuns;

import java.util.Arrays;
import java.util.Optional;

public enum PromocaoProduto {
	NENHUM("NENHUM"), LEVE_2_PAGUE_1("LEVE_2_PAGUE_1"), LEVE_3_PAGUE_10_REAIS("LEVE_3_PAGUE_10_REAIS");

	private String promocao;

	private PromocaoProduto(String promocao) {
		this.promocao = promocao;
	}

	public String getPromocao() {
		return promocao;
	}

	public static Optional<PromocaoProduto> validaPromocao(String promocao) {
		return Arrays.stream(values()).filter(valorCorrespondente -> valorCorrespondente.getPromocao().equals(promocao))
				.findFirst();
	}

}
