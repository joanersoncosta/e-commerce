package br.com.siteware.carrinho.application.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrinho")
public interface CarrinhoAPI {
	
	@PostMapping(path = "/adiciona")
	@ResponseStatus(value = HttpStatus.CREATED)
	CarrinhoIdResponse adicionaProdutoAoCarrinho(@RequestParam(value = "email", required = true) String email, @RequestBody @Valid CarrinhoRequest carrinhoRequest);

	@GetMapping(path = "/busca-produtos")
	@ResponseStatus(value = HttpStatus.OK)
	List<CarrinhoListResponse> listaCarrinhoDoCliente(@RequestParam(value = "email", required = true) String email);

	
}
