package com.identidade;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;

public interface RepositorioMensagens extends Repositorio<Mensagem, Long> {
	
	List<Mensagem> buscarMensagensDoJogo(Jogo jogo);
	
}
