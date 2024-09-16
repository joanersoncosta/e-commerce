package br.com.siteware.config.security.enuns;

import lombok.Getter;

@Getter
public enum AuthUrlMapping {

	CLIENTE(RoleAuth.ROLE_CLIENTE.name(), new String[] {
            	"/v1/cliente/public",
            	"/v1/cliente/public/**",
            	"/v1/produto/public",
            	"/v1/produto/public/**",
	            "/v1/produto/public",
	            "/v1/produto/public/**",
            	"/carrinho/**",
            	"/pedido/**"
	}),
	ADMIN(RoleAuth.ROLE_ADMIN.name(), new String[] {
	            "/v1/produto/admin",
	            "/v1/produto/admin/**",
	            "/v1/produto/public",
	            "/v1/produto/public/**",
	            "/v1/cliente/admin",
	            "/v1/cliente/admin/busca-clientes"
	}),
	PERMIT_ALL(null, new String[] {
	            "/public/**",
	            "/private/admin/cadastro",
	            "/v1/cliente/**",
	            "/v1/cliente/public/cadastro",
            	"/v1/produto/**",
            	"/carrinho/**",
            	"/pedido/**"
	});

    private final String role;
    private final String[] urls;

    AuthUrlMapping(String role, String[] urls) {
        this.role = role;
        this.urls = urls;
    }
}
