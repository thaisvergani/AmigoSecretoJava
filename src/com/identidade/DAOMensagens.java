package com.identidade;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;

public interface DAOMensagens extends DAO<Mensagem, Long> {
	
	List<Mensagem> buscarMensagensDoJogo(Jogo jogo);
		
}
