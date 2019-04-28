package com.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.identidade.DAO;

public abstract class RepositorioBase<T, K> implements DAO<T, K> {

	protected Class<T> entityClass;
	 
	@PersistenceContext(name="AmigoSecreto")
	protected EntityManager em;
		
	@SuppressWarnings("unchecked")
	public RepositorioBase() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	@Override
	public void persistir(T entidade) {
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Override
	public void deletar(T entidade) {
		try {
			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}	
	}

	@Override
	public T buscaPorId(K id) { 
		return em.find(entityClass, id);
	}
}
