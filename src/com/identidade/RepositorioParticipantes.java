package com.identidade;

import java.util.List;

import com.entidades.Participante;

public interface RepositorioParticipantes extends Repositorio<Participante, Long> {
	
	List<Participante> buscarTodosParticipantes();
	
	Participante buscarParticipante(String nomeOuCodinome);
	
}
