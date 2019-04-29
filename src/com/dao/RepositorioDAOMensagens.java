package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.ParticipanteMensagens;
import com.identidade.RepositorioMensagens;

public class RepositorioDAOMensagens extends RepositorioDAOBase<Mensagem, Long> implements RepositorioMensagens {

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
