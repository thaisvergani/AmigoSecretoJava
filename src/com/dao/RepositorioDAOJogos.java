package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Jogo;
import com.entidades.Amizade;
import com.identidade.RepositorioJogos;

public class RepositorioDAOJogos extends RepositorioDAOBase<Jogo, Long> implements RepositorioJogos {
	
	public RepositorioDAOJogos(EntityManager entityManager) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Amizade> buscarAmizadesDoJogo(Jogo jogo) {
		if (jogo == null) {
			return new ArrayList<Amizade>();
		}
		
		return em
			.createQuery("SELECT a FROM Amizade a JOIN a.jogo j WHERE j.id = :idJogo")
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jogo> buscarUltimosJogos(int quantidade) {
		return em
			.createQuery("SELECT j FROM Jogo j ORDER BY j.inicio DESC LIMIT :qty")
			.setParameter("qty", quantidade)
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jogo> buscarTodosJogos() {
		return em
			.createQuery("SELECT j FROM Jogo j")
			.getResultList();
	}
	
	

}
