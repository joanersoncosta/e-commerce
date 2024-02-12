package br.com.siteware;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.siteware.categoria.domain.Categoria;
import br.com.siteware.produto.application.api.AlteraPromocaoProdutoRequest;
import br.com.siteware.produto.application.api.EditaProdutoRequest;
import br.com.siteware.produto.application.api.ProdutoRequest;
import br.com.siteware.produto.application.api.PromocaoProdutoRequest;
import br.com.siteware.produto.domain.EstoqueProdutoStatus;
import br.com.siteware.produto.domain.Produto;
import br.com.siteware.produto.domain.enuns.PromocaoProduto;
import br.com.siteware.produto.domain.enuns.PromocaoProdutoStatus;

public class ProdutoDataHelper {
	public static final UUID ID_PRODUTO = UUID.fromString("4b044033-5cce-4c4c-80a3-70234aff3951");
	public static final UUID ID_USUARIO_VALIDO = UUID.fromString("8d58875d-2455-4075-8b5d-57c73fcf1241");

	public static Produto createProduto() {
		return Produto.builder().idProduto(ID_PRODUTO).categoria(Categoria.ELETRONICO).promocao(PromocaoProduto.NENHUM)
				.statusPromocao(PromocaoProdutoStatus.INATIVO).statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3)
				.nome("Produto 1").descricao("Exemplo Produto 1").preco(704.45).produtosVendidos(0).desconto(0)
				.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
				.dataModificacaoProduto(LocalDateTime.now()).build();
	}

	public static ProdutoRequest createProdutorequest() {
		return new ProdutoRequest("ELETRONICO", 1, 3, "Produto 1", "Exemplo Produto 1", 704.45);
	}

	public static List<Produto> createListProduto() {
		return List.of(Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
				.promocao(PromocaoProduto.NENHUM).statusPromocao(PromocaoProdutoStatus.INATIVO)
				.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 1")
				.descricao("Exemplo Produto 1").preco(704.45).produtosVendidos(0).desconto(0).precoOriginal(704.45)
				.dataCadastroProduto(LocalDateTime.now()).dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.IMOVEL)
						.promocao(PromocaoProduto.PROMOCAO).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 2")
						.descricao("Exemplo Produto 2").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
						.promocao(PromocaoProduto.NENHUM).statusPromocao(PromocaoProdutoStatus.INATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 3")
						.descricao("Exemplo Produto 3").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.COSMETICO)
						.promocao(PromocaoProduto.LEVE_2_PAGUE_1).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 4")
						.descricao("Exemplo Produto 4").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build());
	}

	public static List<Produto> createListProdutoCategoria() {
		return List.of(Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
				.promocao(PromocaoProduto.NENHUM).statusPromocao(PromocaoProdutoStatus.INATIVO)
				.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 1")
				.descricao("Exemplo Produto 1").preco(704.45).produtosVendidos(0).desconto(0).precoOriginal(704.45)
				.dataCadastroProduto(LocalDateTime.now()).dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
						.promocao(PromocaoProduto.PROMOCAO).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 2")
						.descricao("Exemplo Produto 2").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
						.promocao(PromocaoProduto.NENHUM).statusPromocao(PromocaoProdutoStatus.INATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 3")
						.descricao("Exemplo Produto 3").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
						.promocao(PromocaoProduto.LEVE_2_PAGUE_1).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 4")
						.descricao("Exemplo Produto 4").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build());
	}
	
	public static EditaProdutoRequest editaProdutoRequest() {
		return new EditaProdutoRequest("Produto request", "Exemplo request", 782.25);
	}
	
	public static AlteraPromocaoProdutoRequest alteraPromocaoProdutoRequest() {
		return new AlteraPromocaoProdutoRequest(2);
	}
	
	public static List<Produto> createListProdutoComPromocao() {
		return List.of(Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
				.promocao(PromocaoProduto.PROMOCAO).statusPromocao(PromocaoProdutoStatus.ATIVO)
				.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 1")
				.descricao("Exemplo Produto 1").preco(704.45).produtosVendidos(0).desconto(0).precoOriginal(704.45)
				.dataCadastroProduto(LocalDateTime.now()).dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.IMOVEL)
						.promocao(PromocaoProduto.PROMOCAO).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 2")
						.descricao("Exemplo Produto 2").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.ELETRONICO)
						.promocao(PromocaoProduto.LEVE_3_PAGUE_10_REAIS).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 3")
						.descricao("Exemplo Produto 3").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build(),
				Produto.builder().idProduto(UUID.randomUUID()).categoria(Categoria.COSMETICO)
						.promocao(PromocaoProduto.LEVE_2_PAGUE_1).statusPromocao(PromocaoProdutoStatus.ATIVO)
						.statusEstoque(EstoqueProdutoStatus.DISPONIVEL).estoque(3).nome("Produto 4")
						.descricao("Exemplo Produto 4").preco(704.45).produtosVendidos(0).desconto(0)
						.precoOriginal(704.45).dataCadastroProduto(LocalDateTime.now())
						.dataModificacaoProduto(LocalDateTime.now()).build());
	}
	
	public static PromocaoProdutoRequest promocaoProdutoRequest() {
		return new PromocaoProdutoRequest(50);
	}
	
}
