package com.data;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.identidade.DAO;

public abstract class RepositorioBase<T, K> implements DAO<T, K> {

	protected Class<T> entityClass;
	 
	@PersistenceContext
	protected EntityManager em;
		
	public RepositorioBase() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	@Override
	public void persistir(T entidade) {
		em.persist(entidade);
	}

	@Override
	public void deletar(T entidade) {
		em.remove(entidade);
	}

	@Override
	public T buscaPorId(K id) { 
		return em.find(entityClass, id);
	}
}
