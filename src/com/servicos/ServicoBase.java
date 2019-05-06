package com.servicos;

import com.identidade.ContextoAmigoSecreto;
import com.identidade.Repositorio;

public abstract class ServicoBase<T, K> {

	protected ContextoAmigoSecreto contexto;
	protected Repositorio<T, K> repositorio;
	
	public ServicoBase(ContextoAmigoSecreto contexto, Repositorio<T, K> repositorio) throws IllegalArgumentException {
		if (contexto == null) {
			throw new IllegalArgumentException("Contexto da aplicacao nao pode ser nulo");
		}
		
		this.contexto = contexto;
		this.repositorio = repositorio;
	}			
	
	
	public void deletar(T entidade) {
		repositorio.deletar(entidade);
	}
	
	public T buscaPorId(K id) {
		return repositorio.buscaPorId(id);
	}
}
