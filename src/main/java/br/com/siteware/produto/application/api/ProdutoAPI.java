package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1/Produto")
public interface ProdutoAPI {

	@PostMapping(path = "/admin/cadastra")
	@ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Cadastra novo produto")
	ProdutoIdResponse cadastraProduto(@RequestHeader(name = "Authorization", required = true) String token, @RequestBody @Valid ProdutoRequest produtoRequest);

	@GetMapping(path = "/public/{idProduto}/busca")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Busca produto por ID") 
	ProdutoDetalhadoResponse buscaProdutoPorId(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto);

	@GetMapping(path = "/public/busca")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Lista todos os produtos")
	List<ProdutoListResponse> buscaTodosOsProdutos(@RequestHeader(name = "Authorization", required = true) String token);

	@GetMapping(path = "/public/busca/categoria")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Lista todos os produtos por categoria")
	List<ProdutoListResponse> buscaProdutosPorCategoria(@RequestHeader(name = "Authorization", required = true) String token, @PathParam(value = "categoria") String categoria);

	@GetMapping(path = "/public/busca/nome")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Lista todos os produtos por nome")
	List<ProdutoListResponse> buscaProdutosPorNome(@RequestHeader(name = "Authorization", required = true) String token, @RequestParam(name = "nome") String nomeProduto);

	@DeleteMapping(path = "/admin/{idProduto}/deleta")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Admin deleta produto por ID")
	void deletaProdutoPorId(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto);

	@PatchMapping(path = "/admin/{idProduto}/edita")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Admin edita produto por ID")
	void editaProdutoPorId(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto, @RequestBody @Valid EditaProdutoRequest editaProduto);

	@PatchMapping(path = "/admin/{idProduto}/promocao/altera")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Admin altera promocao do produto")
	void alteraPromocaoDoProdutoPorId(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto, @RequestBody @Valid AlteraPromocaoProdutoRequest editaPromocaoProduto);

	@GetMapping(path = "/public/busca/promocao")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Lista todos os produtos com promocao")
	List<ProdutoListResponse> buscaProdutoComPromocao(@RequestHeader(name = "Authorization", required = true) String token);

	@PatchMapping(path = "/admin/{idProduto}/promocao/inicia")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Admin aplica promocao ao produto")
	void aplicaPromocaoAoProduto(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto, @PathParam(value = "percentualDesconto") Integer percentualDesconto);

	@PatchMapping(path = "/admin/{idProduto}/promocao/encerra")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "Admin encerra promocao do produto")
	void encerraPromocaoDoProduto(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(value = "idProduto") UUID idProduto);

}
