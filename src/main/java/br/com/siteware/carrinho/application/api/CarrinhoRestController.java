package br.com.siteware.carrinho.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siteware.carrinho.application.service.CarrinhoService;
import br.com.siteware.config.security.service.TokenService;
import br.com.siteware.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController
@Log4j2
public class CarrinhoRestController implements CarrinhoAPI {
	private final CarrinhoService carrinhoService;
	private final TokenService tokenService;

	@Override
	public CarrinhoIdResponse adicionaProdutoAoCarrinho(String token, CarrinhoRequest carrinhoRequest) {
		log.info("[inicia] CarrinhoRestController - adicionaProdutoAoCarrinho");
		var email = getUsuarioByToken(token);
		CarrinhoIdResponse carrinhoIdResponse = carrinhoService.adicionaProdutoAoCarrinho(email, carrinhoRequest);
		log.info("[finaliza] CarrinhoRestController - adicionaProdutoAoCarrinho");
		return carrinhoIdResponse;
	}

	@Override
	public List<CarrinhoListResponse> listaCarrinhoDoCliente(String token, UUID idCliente) {
		log.info("[inicia] CarrinhoRestController - listaCarrinhoDoCliente");
		var email = getUsuarioByToken(token);
		List<CarrinhoListResponse> carrinhoResponse = carrinhoService.listaCarrinhoDoCliente(email, idCliente);
		log.info("[finaliza] CarrinhoRestController - listaCarrinhoDoCliente");
		return carrinhoResponse;
	}

	@Override
	public void removeCarrinho(String token, UUID idCarrinho) {
		log.info("[inicia] CarrinhoRestController - removeCarrinho");
		var email = getUsuarioByToken(token);
		carrinhoService.removeCarrinho(email, idCarrinho);
		log.info("[finaliza] CarrinhoRestController - removeCarrinho");
	}

	@Override
	public void editaCarrinho(String token, UUID idCarrinho, Integer quantidade) {
		log.info("[inicia] CarrinhoRestController - editaCarrinho");
		var email = getUsuarioByToken(token);
		carrinhoService.editaCarrinho(email, idCarrinho, quantidade);
		log.info("[finaliza] CarrinhoRestController - editaCarrinho");
	}
	
	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token).orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}

}
