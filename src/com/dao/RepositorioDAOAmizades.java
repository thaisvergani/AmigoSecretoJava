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
				.createQuery("SELECT p FROM Participante p join Amizade a WHERE a.amigoSecreto = p.id and a.participante = :participante and a.jogo = :jogo")
				.setParameter("participante", participante.getId())
				.setParameter("jogo", jogo.getId())
				.getSingleResult();
		// TODO tem que ter o join pra conseguir retornar o objeto do participante (Amizada.amigoSecreto)?
		// da pra selecionar direto o objeto participante?
		Participante amigo_secreto = (Participante)amigo_da_amizade;
			
		if (amigo_secreto != null) {
			return amigo_secreto;
		}
		
		return null;
		
	}
	
	

}
