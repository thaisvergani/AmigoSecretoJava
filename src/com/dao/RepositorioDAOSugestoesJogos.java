package com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.SugestaoAmigoSecreto;
import com.identidade.RepositorioSugestoesJogos;

public class RepositorioDAOSugestoesJogos extends RepositorioDAOBase<SugestaoAmigoSecreto, Long> implements RepositorioSugestoesJogos {

	public RepositorioDAOSugestoesJogos(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoAmigoSecreto> buscarTodasSugestoes() {
		return em
			.createQuery("SELECT sas FROM SugestaoAmigoSecreto sas")
			.getResultList();
	}

}
