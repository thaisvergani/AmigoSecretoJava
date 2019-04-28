package com.dao;

import java.util.List;

import com.entidades.SugestaoAmigoSecreto;
import com.identidade.RepositorioSugestoesJogos;

public class RepositorioDAOSugestoesJogos extends RepositorioDAOBase<SugestaoAmigoSecreto, Long> implements RepositorioSugestoesJogos {

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoAmigoSecreto> buscarTodasSugestoes() {
		return em
			.createQuery("SELECT * FROM SugestaoAmigoSecreto")
			.getResultList();
	}

}
