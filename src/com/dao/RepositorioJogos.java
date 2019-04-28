package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.entidades.*;
import com.identidade.DAOJogos;

public class RepositorioJogos extends RepositorioBase<Jogo, Long> implements DAOJogos {
	
	public void persistirAmizadesDoJogo(List<Amizade> amizades) {
		if (amizades == null || amizades.size() == 0) {
			return;
		}
		
		try {
			em.getTransaction().begin();
			
			for (int i = 0; i < amizades.size(); i++) {
				Amizade amizade = amizades.get(i);
				em.persist(amizade);
			}
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Participante> buscarParticipantesDoJogo(Jogo jogo) {
		if (jogo == null) {
			return new ArrayList<Participante>();
		}
		
		return em
			.createQuery("SELECT p FROM Amizade a JOIN a.participante p JOIN a.jogo j WHERE j.id = :idJogo")
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}

}
