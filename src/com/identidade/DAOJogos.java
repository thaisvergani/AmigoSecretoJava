package com.identidade;

import java.util.List;

import com.entidades.*;

public interface DAOJogos extends DAO<Jogo, Long> {	
	List<Participante> buscarParticipantesDoJogo(Jogo jogo);
	void persistirAmizadesDoJogo(List<Amizade> amizades);
}
