package com.dao;

import java.util.List;

import com.entidades.SugestaoAmigoSecreto;
import com.identidade.DAOSugestoesJogos;

public class RepositorioSugestoesJogos extends RepositorioBase<SugestaoAmigoSecreto, Long> implements DAOSugestoesJogos {

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoAmigoSecreto> buscarTodasSugestoes() {
		return em
			.createQuery("SELECT * FROM SugestaoAmigoSecreto")
			.getResultList();
	}

}
