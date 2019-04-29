package com.identidade;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Amizade;

public interface RepositorioJogos extends Repositorio<Jogo, Long> {
	
	List<Jogo> buscarTodosJogos();
	
	List<Jogo> buscarUltimosJogos(int quantidade);
	
	List<Amizade> buscarAmizadesDoJogo(Jogo jogo);
	
	void persistirAmizadesDoJogo(List<Amizade> amizades);
	
}
