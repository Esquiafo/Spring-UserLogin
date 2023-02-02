package com.publiedicto.enumerados;

public enum Rol {

	ADMINISTRADOR("ADMINISTRADOR"), CLIENTE("CLIENTE");

	Rol(String texto) {
		this.texto = texto;
	}

	private final String texto;

}
