package com.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import com.identidade.Repositorio;

public abstract class RepositorioDAOBase<T, K> implements Repositorio<T, K> {

	protected Class<T> entityClass;
	 	
	protected EntityManager em;			
		
	@SuppressWarnings("unchecked")
	public RepositorioDAOBase(EntityManager entityManager) {
		if (entityManager == null) {
			throw new IllegalArgumentException("Necessario informar o entity manager da aplicacao");
		}
		
		em = entityManager;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	@Override
	public void adicionar(T entidade) {
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
	public void atualizar(T entidade) {
		try {
			em.getTransaction().begin();
			em.merge(entidade);
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
