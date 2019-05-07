package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;
import com.identidade.RepositorioMensagens;

public class RepositorioDAOMensagens extends RepositorioDAOBase<Mensagem, Long> implements RepositorioMensagens {

	public RepositorioDAOMensagens(EntityManager entityManager) {
		super(entityManager);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Mensagem> buscarMensagensParticipante(Participante participante) {
		if (participante == null) {
			return new ArrayList<Mensagem>();
		}
		
		return em
			.createQuery("SELECT m FROM Mensagem m  WHERE m.destinatario = :idparticipante")
			.setParameter("idparticipante", participante)
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mensagem> buscarMensagensDoJogo(Jogo jogo) {
		if (jogo == null) {
			return new ArrayList<Mensagem>();
		}
		
		return em
			.createQuery("SELECT m FROM Mensagem m JOIN m.jogo j WHERE j.id = :idJogo")
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int totalMensagens(Jogo jogo) {
		if (jogo == null) {
			return 0;
		}
		
		List<Mensagem> lista = em
				.createQuery("SELECT m FROM Mensagem m JOIN m.jogo j WHERE j.id = :idJogo")
				.setParameter("idJogo", jogo.getId())
				.getResultList();
		return lista.size();
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participante> rankingEnviouMaisMensagens(Jogo jogo) {
		return em
				.createQuery("SELECT d FROM Mensagem m JOIN m.remetente d WHERE m.jogo = :idJogo GROUP BY d.id ORDER BY COUNT(m) DESC")
				.setParameter("idJogo", jogo)
				.getResultList();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participante> rankingRecebeuMaisMensagens(Jogo jogo) {
		return em
			.createQuery("SELECT d FROM Mensagem m JOIN m.destinatario d WHERE m.jogo = :idJogo GROUP BY d.id ORDER BY COUNT(m) DESC")
			.setParameter("idJogo", jogo)
			.getResultList();
	}
	
	

}
