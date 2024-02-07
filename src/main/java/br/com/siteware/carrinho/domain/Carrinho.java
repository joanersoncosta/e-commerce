package br.com.siteware.carrinho.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "Carrinho")
public class Carrinho {

	@Id
	private UUID idCarrinho;
	@Indexed
	private UUID idCliente;
	@Indexed
	private UUID idProduto;
	@NotNull
	private PromocaoProduto promocao;
	@NotNull
	private String nome;
	@NotBlank(message = "Campo descrição produto não pode estar vazio")
	@Size(max = 255, min = 3)
	private String descricao;
	private Double preco;
	private int quantidade;
}
