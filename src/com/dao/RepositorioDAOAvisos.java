package com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Aviso;
import com.identidade.RepositorioAvisos;

public class RepositorioDAOAvisos extends RepositorioDAOBase<Aviso, Long> implements RepositorioAvisos {

	public RepositorioDAOAvisos(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aviso> buscarTodosAvisos() {
		return em
			.createQuery("SELECT a FROM Aviso a")
			.getResultList();
	}

}
