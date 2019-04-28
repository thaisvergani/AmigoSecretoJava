package com.identidade;

import java.util.List;

import com.entidades.SugestaoPresente;

public interface RepositorioSugestoesPresentes extends Repositorio<SugestaoPresente, Long> {
	
	List<SugestaoPresente> buscarSugestoes(String nomeParticipante);
	
}
