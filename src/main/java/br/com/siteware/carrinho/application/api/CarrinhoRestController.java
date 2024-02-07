package br.com.siteware.carrinho.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.carrinho.application.service.CarrinhoService;
import jakarta.validation.Valid;
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

	@Override
	public void removeCarrinho(String email, UUID idCarrinho) {
		log.info("[inicia] CarrinhoRestController - removeCarrinho");
		carrinhoService.removeCarrinho(email, idCarrinho);
		log.info("[finaliza] CarrinhoRestController - removeCarrinho");
	}

	@Override
	public void editaCarrinho(String email, UUID idCarrinho, EditaCarrinhoRequest carrinhoRequest) {
		log.info("[inicia] CarrinhoRestController - editaCarrinho");
		carrinhoService.editaCarrinho(email, idCarrinho, carrinhoRequest);
		log.info("[finaliza] CarrinhoRestController - editaCarrinho");
	}

}
