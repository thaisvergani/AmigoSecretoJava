package com.dao;

import java.util.List;

import com.entidades.Aviso;
import com.identidade.RepositorioAvisos;

public class RepositorioDAOAvisos extends RepositorioDAOBase<Aviso, Long> implements RepositorioAvisos {

	@SuppressWarnings("unchecked")
	@Override
	public List<Aviso> buscarTodosAvisos() {
		return em
			.createQuery("SELECT * FROM Aviso")
			.getResultList();
	}

}
