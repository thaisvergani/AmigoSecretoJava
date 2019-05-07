package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.Amizade;
import com.identidade.RepositorioAmizades;
import com.identidade.RepositorioJogos;

public class RepositorioDAOAmizades extends RepositorioDAOBase<Amizade, Long> implements RepositorioAmizades {
	
	public RepositorioDAOAmizades(EntityManager entityManager) {
		super(entityManager);
	}

	public void persistirAmizadesDoJogo(List<Amizade> amizades) {
		if (amizades == null || amizades.size() == 0) {
			return;
		}
		
		try {
			em.getTransaction().begin();
			for	(Amizade amizade : amizades) {
				em.persist(amizade);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	@Override
	public Participante buscarAmigo(Jogo jogo, Participante participante) {
		

		Object amigo_da_amizade = em
				.createQuery("SELECT s FROM Amizade a JOIN a.amigoSecreto s WHERE a.participante = :participante and a.jogo = :jogo")
				.setParameter("participante", participante)
				.setParameter("jogo", jogo)
				.getSingleResult();
	
		Participante amigo_secreto = (Participante)amigo_da_amizade;
			
		if (amigo_secreto != null) {
			return amigo_secreto;
		}
		
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Amizade> buscarAmizadesDoJogo(Jogo jogo) {
		if (jogo == null) {
			return new ArrayList<Amizade>();
		}
		
		return em
			.createQuery("SELECT a FROM Amizade a JOIN a.jogo j JOIN a.amigoSecreto p1 JOIN a.participante p2 WHERE j.id = :idJogo")
			.setParameter("idJogo", jogo.getId())
			.getResultList();
		
		// TODO isso vai retornar os objetos de participante tambem??
	}
	
	

}
