package com.identidade;

public interface Repositorio<T, K> {
	void adicionar(T entidade);
	void atualizar(T entidade);
	void deletar(T entidade);
	T buscaPorId(K id);
}
