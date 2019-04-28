package com.servicos;

import com.identidade.ContextoAmigoSecreto;

public abstract class ServicoBase {

	protected ContextoAmigoSecreto contexto;
	
	public ServicoBase(ContextoAmigoSecreto contexto) throws IllegalArgumentException {
		if (contexto == null) {
			throw new IllegalArgumentException("Contexto da aplica��o n�o pode ser nulo");
		}
		
		this.contexto = contexto;
	}
	
}
