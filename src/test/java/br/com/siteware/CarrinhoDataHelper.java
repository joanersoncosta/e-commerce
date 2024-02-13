package br.com.siteware;

import java.util.UUID;

import br.com.siteware.carrinho.application.api.CarrinhoRequest;

public class CarrinhoDataHelper {
	public static final UUID ID_CARRINHO = UUID.fromString("46a007c7-2321-4e1b-9469-b780fda14571");
	public static final UUID ID_PRODUTO = UUID.fromString("4b044033-5cce-4c4c-80a3-70234aff3951");
	
	public static CarrinhoRequest carrinhoRequest() {
		return new CarrinhoRequest(ID_PRODUTO, 1);
	}
}
