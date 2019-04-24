package com.identidade;

public interface DAO<T, K> {
	void persistir(T entidade);
	void deletar(T entidade);
	T buscaPorId(K id);
}
