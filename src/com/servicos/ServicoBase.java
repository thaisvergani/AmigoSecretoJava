package com.servicos;

import com.identidade.ContextoAmigoSecreto;

public abstract class ServicoBase {

	protected ContextoAmigoSecreto contexto;
	
	public ServicoBase(ContextoAmigoSecreto contexto) throws IllegalArgumentException {
		if (contexto == null) {
			throw new IllegalArgumentException("Contexto da aplicacao nao pode ser nulo");
		}
		
		this.contexto = contexto;
	}
	
}
