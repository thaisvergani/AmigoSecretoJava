package com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.SugestaoPresente;
import com.identidade.RepositorioSugestoesPresentes;

public class RepositorioDAOSugestoesPresentes extends RepositorioDAOBase<SugestaoPresente, Long> implements RepositorioSugestoesPresentes {

	public RepositorioDAOSugestoesPresentes(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoPresente> buscarSugestoes(Participante participante, Jogo jogo) {
		return em
			.createQuery("SELECT sp FROM SugestaoPresente sp JOIN sp.participante p JOIN sp.jogo j WHERE p.id = :idParticipante AND j.id = :idJogo")
			.setParameter("idParticipante", participante.getId())
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SugestaoPresente> buscarTodasSugestoes() {
		return em
			.createQuery("SELECT sp FROM SugestaoPresente sp JOIN sp.participante p JOIN sp.jogo j")
			.getResultList();
	}
}
