package br.com.siteware.cliente.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteIdResponse {
	private UUID idCliente;
}
