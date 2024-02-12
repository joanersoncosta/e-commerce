package br.com.siteware.produto.application.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PromocaoProdutoRequest {
	private Integer percentualDesconto;
}
