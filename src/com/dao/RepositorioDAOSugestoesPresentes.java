package com.dao;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.SugestaoPresente;
import com.identidade.RepositorioSugestoesPresentes;

public class RepositorioDAOSugestoesPresentes extends RepositorioDAOBase<SugestaoPresente, Long> implements RepositorioSugestoesPresentes {

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoPresente> buscarSugestoes(Participante participante, Jogo jogo) {
		return em
			.createQuery("SELECT sp FROM SugestaoPresente sp JOIN sp.participante p JOIN sp.jogo j WHERE p.id = :idParticipante AND j.id = :idJogo")
			.setParameter("idParticipante", participante.getId())
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}
}