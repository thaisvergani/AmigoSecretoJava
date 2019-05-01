package com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Participante;
import com.identidade.RepositorioParticipantes;

public class RepositorioDAOParticipantes extends RepositorioDAOBase<Participante, Long> implements RepositorioParticipantes {
	
	public RepositorioDAOParticipantes(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Participante buscarParticipante(String nomeOuCodinome) {
		Object participante = em
			.createQuery("SELECT p FROM Participante p WHERE p.nome = :nome OR p.codinome = :nome")
			.setParameter("nome", nomeOuCodinome)
			.getSingleResult();
		
		if (participante != null) {
			return (Participante)participante;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participante> buscarTodosParticipantes() {
		return em
			.createQuery("SELECT p FROM Participante p")
			.getResultList();		
	}
}
