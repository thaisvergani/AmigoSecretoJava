package com.identidade;

import java.util.List;

import com.model.ejb.entity.Jogo;
import com.model.ejb.entity.Mensagem;

public interface DAOMensagens extends DAO<Mensagem, Long> {
	
	List<Mensagem> buscarMensagensDoJogo(Jogo jogo);
	
	
}
