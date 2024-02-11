package br.com.siteware.produto.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/v1/produto")
public interface ProdutoAPI {

	@PostMapping(path = "/cadastra")
	@ResponseStatus(value = HttpStatus.CREATED)
	ProdutoIdResponse cadastraProduto(@PathParam(value = "email") String email, @RequestBody @Valid ProdutoRequest produtoRequest);

	@GetMapping(path = "/{idProduto}/busca")
	@ResponseStatus(value = HttpStatus.OK)
	ProdutoDetalhadoResponse buscaProdutoPorId(@PathVariable(value = "idProduto") UUID idProduto);

	@GetMapping(path = "/busca-produtos")
	@ResponseStatus(value = HttpStatus.OK)
	List<ProdutoListResponse> buscaTodosOsProdutos();

	@GetMapping(path = "/busca-produtos-por-categoria")
	@ResponseStatus(value = HttpStatus.OK)
	List<ProdutoListResponse> buscaProdutosPorCategoria(@PathParam(value = "categoria") String categoria);

	@GetMapping(path = "/busca-produtos-por-nome")
	@ResponseStatus(value = HttpStatus.OK)
	List<ProdutoListResponse> buscaProdutosPorNome(@RequestParam(name = "nome") String nomeProduto);

	@DeleteMapping(path = "/{idProduto}/deleta")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void deletaProdutoPorId(@PathVariable(value = "idProduto") UUID idProduto);

	@PatchMapping(path = "/{idProduto}/edita")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void editaProdutoPorId(@PathParam(value = "email") String email, @PathVariable(value = "idProduto") UUID idProduto, @RequestBody @Valid EditaProdutoRequest editaProduto);

	@PatchMapping(path = "/{idProduto}/altera-promocao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void alteraPromocaoDoProdutoPorId(@PathParam(value = "email") String email, @PathVariable(value = "idProduto") UUID idProduto, @RequestBody @Valid AlteraPromocaoProdutoRequest editaPromocaoProduto);

	@GetMapping(path = "/busca-produtos-promocao")
	@ResponseStatus(value = HttpStatus.OK)
	List<ProdutoListResponse> buscaProdutoComPromocao();

	@PatchMapping(path = "/{idProduto}/promocao/inicia")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void aplicaPromocaoAoProduto(@PathParam(value = "email") String email, @PathVariable(value = "idProduto") UUID idProduto, @RequestBody @Valid PromocaoProdutoRequest promocaoRequest);

	@PatchMapping(path = "/{idProduto}/promocao/encerra")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void encerraPromocaoDoProduto(@PathParam(value = "email") String email, @PathVariable(value = "idProduto") UUID idProduto);

}
