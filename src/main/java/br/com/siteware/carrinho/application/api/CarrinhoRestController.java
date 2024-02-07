package br.com.siteware.carrinho.application.api;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.carrinho.application.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController
@Log4j2
public class CarrinhoRestController implements CarrinhoAPI {
	private final CarrinhoService carrinhoService;

	@Override
	public CarrinhoIdResponse adicionaProdutoAoCarrinho(String email, CarrinhoRequest carrinhoRequest) {
		log.info("[inicia] CarrinhoRestController - adicionaProdutoAoCarrinho");
		CarrinhoIdResponse carrinhoIdResponse = carrinhoService.adicionaProdutoAoCarrinho(email, carrinhoRequest);
		log.info("[finaliza] CarrinhoRestController - adicionaProdutoAoCarrinho");
		return carrinhoIdResponse;
	}

	@Override
	public List<CarrinhoListResponse> listaCarrinhoDoCliente(String email) {
		log.info("[inicia] CarrinhoRestController - listaCarrinhoDoCliente");
		List<CarrinhoListResponse> carrinhoResponse = carrinhoService.listaCarrinhoDoCliente(email);
		log.info("[finaliza] CarrinhoRestController - listaCarrinhoDoCliente");
		return carrinhoResponse;
	}

}
