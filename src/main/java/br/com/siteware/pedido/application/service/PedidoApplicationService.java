package br.com.siteware.pedido.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.siteware.carrinho.application.repository.CarrinhoRepository;
import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.repository.ClienteRepository;
import br.com.siteware.cliente.application.service.ClienteService;
import br.com.siteware.cliente.domain.Cliente;
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
	public PedidoDetalhadoResponse buscaPedido(String email) {
		log.info("[inicia] PedidoApplicationService - buscaPedido");
		Cliente cliente = clienteRepository.detalhaClientePorEmail(email);	
		log.info("[cliente] {}", cliente);
		List<Carrinho> carrinhoDoCliente = carrinhoRepository.listaCarrinhoDoCliente(cliente.getIdCliente());
		log.info("[finaliza] PedidoApplicationService - buscaPedido");
		return PedidoDetalhadoResponse.converte(cliente.getIdCliente(), carrinhoDoCliente);
	}

}
