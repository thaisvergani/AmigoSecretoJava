package com.dao;

import java.util.List;

import com.entidades.*;
import com.identidade.DAOSugestoesPresentes;

public class RepositorioSugestoesPresentes extends RepositorioBase<SugestaoPresente, Long> implements DAOSugestoesPresentes {

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoPresente> buscarSugestoes(String participante) {
		return em
			.createQuery("SELECT sp FROM SugestaoPresente sp JOIN sp.participante p WHERE p.nome = :nome OR p.codinome = :nome")
			.setParameter("nome", participante)
			.getResultList();
	}
}
