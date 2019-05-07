package com.identidade;

import java.util.List;

import com.entidades.Amizade;
import com.entidades.Jogo;
import com.entidades.Participante;

public interface RepositorioAmizades extends Repositorio<Amizade, Long> {
		
	Participante buscarAmigo(Jogo jogo, Participante participante);

	List<Amizade> buscarAmizadesDoJogo(Jogo jogo);
	
}
