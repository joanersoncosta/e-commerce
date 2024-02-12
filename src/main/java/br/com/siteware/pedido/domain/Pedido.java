package br.com.siteware.pedido.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import br.com.siteware.carrinho.domain.Carrinho;
import br.com.siteware.cliente.application.api.ClienteDetalhadoResponse;
import br.com.siteware.handler.APIException;
import br.com.siteware.pedido.domain.enuns.PedidoStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "Pedido")
public class Pedido {

	@Id
	private UUID idPedido;
	@Indexed
	private UUID idCliente;
	@NotNull
	private PedidoStatus pedidoStatus;
	private LocalDateTime momentoDoPedido;
	@Digits(integer=5, fraction=2)
	private Double total;
	
	public void pertenceAoCliente(ClienteDetalhadoResponse cliente) {
		if (!this.idCliente.equals(cliente.getIdCliente())) {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não é dono do Pedido solicitado!");
		}
	}
	
	private static Double calculaTotalPedido(List<Carrinho> carrinho) {
		Double soma = carrinho.stream()
				.map(p -> p.getSubTotal())
				.reduce(0.0, (x, y) -> x + y);
		return soma;
	}
}
