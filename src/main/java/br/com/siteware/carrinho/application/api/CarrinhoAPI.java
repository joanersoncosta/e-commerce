package br.com.siteware.carrinho.application.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinho")
public interface CarrinhoAPI {
	
	@PostMapping(path = "/adiciona")
	@ResponseStatus(value = HttpStatus.CREATED)
	CarrinhoIdResponse adicionaProdutoAoCarrinho(@RequestHeader(name = "Authorization", required = true) String token, @RequestBody @Valid CarrinhoRequest carrinhoRequest);

	@GetMapping(path = "/cliente/{idCliente}/busca-produtos")
	@ResponseStatus(value = HttpStatus.OK)
	List<CarrinhoListResponse> listaCarrinhoDoCliente(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idCliente") UUID idCliente);

	@DeleteMapping(path = "/{idCarrinho}/remove")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void removeCarrinho(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idCarrinho") UUID idCarrinho);

	@PatchMapping(path = "/{idCarrinho}/edita")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void editaCarrinho(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idCarrinho") UUID idCarrinho, @PathParam(value = "quantidade") Integer quantidade);

}
