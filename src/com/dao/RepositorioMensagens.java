package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.identidade.DAOMensagens;

public class RepositorioMensagens extends RepositorioBase<Mensagem, Long> implements DAOMensagens {

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

}
