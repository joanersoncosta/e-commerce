package br.com.siteware.pedido.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.domain.Cliente;
import br.com.siteware.handler.APIException;
import br.com.siteware.pedido.application.api.PedidoDetalhadoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PedidoApplicationService implements PedidoService {
	private final ClienteRepository clienteRepository;
	private final CarrinhoRepository carrinhoRepository;

	@Override
	public PedidoDetalhadoResponse buscaPedido(String email, UUID idCliente) {
		log.info("[inicia] PedidoApplicationService - buscaPedido");
		Cliente clienteEmail = clienteRepository.detalhaClientePorEmail(email);	
		log.info("[clienteEmail] {}", clienteEmail);
		Cliente cliente = clienteRepository.detalhaClientePorId(idCliente)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
		cliente.pertenceAoCliente(cliente);
		List<Carrinho> carrinhoDoCliente = carrinhoRepository.listaCarrinhoDoCliente(cliente.getIdCliente());
		log.info("[finaliza] PedidoApplicationService - buscaPedido");
		return PedidoDetalhadoResponse.converte(cliente.getIdCliente(), carrinhoDoCliente);
	}

}
