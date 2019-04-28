package com.identidade;

import java.util.List;

import com.entidades.Participante;

public interface DAOParticipantes extends DAO<Participante, Long> {

	List<Participante> buscarTodosParticipantes();
	
	Participante buscarParticipante(String nomeOuCodinome);	
}
