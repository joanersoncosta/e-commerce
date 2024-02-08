package br.com.siteware.pedido.application.api;

import java.util.List;
import java.util.UUID;

import br.com.siteware.carrinho.domain.Carrinho;
import jakarta.validation.constraints.Digits;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PedidoDetalhadoResponse {
	private UUID idCliente;
	@Digits(integer=5, fraction=2)
	private Double total;
	
	private PedidoDetalhadoResponse(UUID idCliente, Double total) {
		this.idCliente = idCliente;
		this.total = total;
	}
	
	public static PedidoDetalhadoResponse converte(UUID idCliente, List<Carrinho> carrinhoDoCliente) {
		Double total = calculaTotalPedido(carrinhoDoCliente);
		return new PedidoDetalhadoResponse(idCliente, total);
	}
	
	private static Double calculaTotalPedido(List<Carrinho> carrinho) {
		Double soma = carrinho.stream()
				.map(p -> p.getSubTotal())
				.reduce(0.0, (x, y) -> x + y);
		return soma;
	}

}
