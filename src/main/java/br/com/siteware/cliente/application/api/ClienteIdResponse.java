package br.com.siteware.cliente.application.api;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteIdResponse {
	@Schema(description = "Este é o ID do Cliente")
	private UUID idCliente;
}
