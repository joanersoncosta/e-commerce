package br.com.siteware.cliente.application.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;

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
