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
			.createQuery("SELECT m FROM Mensagem m JOIN m.destinatario p WHERE p.id = :idparticipante")
			.setParameter("idparticipante", participante.getId())
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
	public List<ParticipanteMensagens> rankingEnviouMaisMensagens(Jogo jogo) {
		return em
			.createQuery("SELECT r, COUNT(m.id) as totalMensagens FROM Mensagem m JOIN m.jogo j JOIN m.remetente r WHERE j.id = :idJogo GROUP BY r ORDER BY COUNT(r) DESC")
			.setParameter("idJogo", jogo.getId())
			.getResultList();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipanteMensagens> rankingRecebeuMaisMensagens(Jogo jogo) {
		return em
			.createQuery("SELECT d, COUNT(m.id) as totalMensagens FROM Mensagem m JOIN m.jogo j JOIN m.destinatario d WHERE j.id = :idJogo GROUP BY d ORDER BY COUNT(d) DESC")
			.setParameter("idJogo", jogo.getId())
			.getResultList();
	}
	
	

}
