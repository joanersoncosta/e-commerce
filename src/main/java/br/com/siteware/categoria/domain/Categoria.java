package br.com.siteware.categoria.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Categoria {
	ELETRONICO("ELETRONICO"), IMOVEL("IMOVEL"), COSMETICO("COSMETICO"), PET("PET"), PAPELARIA("PAPELARIA");
	
	private String categoria;
	
	Categoria(String  categoria){
		this.categoria = categoria;
	}
	
	public String getCategoria() {
		return this.categoria;
	}
	
	public static Optional<Categoria> validaCategoria(String categoria) {
		return Arrays.stream(values()).filter(valorCorrespondente -> valorCorrespondente.getCategoria().equals(categoria.toUpperCase()))
				.findFirst();
	}
}