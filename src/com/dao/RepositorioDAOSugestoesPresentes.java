package com.dao;

import java.util.List;

import com.entidades.Participante;
import com.entidades.SugestaoPresente;
import com.identidade.RepositorioSugestoesPresentes;

public class RepositorioDAOSugestoesPresentes extends RepositorioDAOBase<SugestaoPresente, Long> implements RepositorioSugestoesPresentes {

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoPresente> buscarSugestoes(Participante participante) {
		return em
			.createQuery("SELECT sp FROM SugestaoPresente sp JOIN sp.participante p WHERE p.id = :idParticipante")
			.setParameter("idParticipante", participante.getId())
			.getResultList();
	}
}
