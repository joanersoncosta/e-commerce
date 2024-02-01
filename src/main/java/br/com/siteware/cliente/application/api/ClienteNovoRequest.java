package br.com.siteware.cliente.application.api;

import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ClienteNovoRequest {
	
	@NotBlank(message = "Campo nome não pode está vazio.")
	private String nome;
	@Email
	@NotNull(message = "Campo email não pode ser nulo.")
	@Indexed(unique = true)
	private String email;
	@NotNull
	@Size(min = 6, max = 10, message = "Digite novamente a senha.")
	private String senha;
	@NotNull(message = "Digite novamente o sexo.")
	private String sexo;
	@NotNull(message = "Digite novamente a data de nascimento.")
	private String dataNascimento;

}
