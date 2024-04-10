package br.com.siteware.produto.application.api;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class PromocaoProdutoRequest{
	@NotNull
	private Integer percentualDesconto;

	public PromocaoProdutoRequest(Integer percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}
	
}