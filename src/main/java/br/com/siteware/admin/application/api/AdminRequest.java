package br.com.siteware.admin.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class AdminRequest {
	@NotBlank
	private String email;
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;
}
