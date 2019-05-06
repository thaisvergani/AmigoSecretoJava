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
	
		Object amizade = em
				.createQuery("SELECT a FROM Amizade a WHERE a.participante = :participante and  a.jogo = :jogo")
				.setParameter("participante", participante.getId())
				.setParameter("jogo", jogo.getId())
				.getSingleResult();
	
		Amizade amizade_obj = (Amizade)amizade;
		
		Participante amigo_secreto= amizade_obj.getAmigoSecreto();

			
		if (amigo_secreto != null) {
			return amigo_secreto;
		}
		
		return null;
		
	}
	
	

}
