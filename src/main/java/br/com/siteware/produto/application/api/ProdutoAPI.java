package br.com.siteware.produto.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/v1/produto")
public interface ProdutoAPI {

	@PostMapping(path = "/cadastra-produto")
	@ResponseStatus(value = HttpStatus.CREATED)
	ProdutoIdResponse cadastraProduto(@PathParam(value = "email") String email, @RequestBody @Valid ProdutoRequest produtoRequest);

}
