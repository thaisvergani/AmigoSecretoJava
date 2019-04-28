package com.dao;

import java.util.List;

import com.entidades.Participante;
import com.identidade.DAOParticipantes;

public class RepositorioParticipantes extends RepositorioBase<Participante, Long> implements DAOParticipantes {
	
	@Override
	public Participante buscarParticipante(String nomeOuCodinome) {
		Object participante = em
			.createQuery("SELECT * FROM Participante p WHERE p.nome = :nome OR p.codinome = :nome")
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
			.createQuery("SELECT * FROM Participante")
			.getResultList();		
	}
}
