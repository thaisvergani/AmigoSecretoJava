package com.identidade;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.SugestaoPresente;

public interface RepositorioSugestoesPresentes extends Repositorio<SugestaoPresente, Long> {
	
	List<SugestaoPresente> buscarSugestoes(Participante participante, Jogo jogo);
	
}
