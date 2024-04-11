package br.com.siteware.admin.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AdminRequest {
	@NotBlank
	@Schema(description = "Este é o E-mail do admin", example = "admin@gmail.com")
	private String email;
	@Schema(description = "Esta é a senha do cliente", example = "admin123")
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;
}
