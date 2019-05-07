package com.identidade;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;

public interface RepositorioMensagens extends Repositorio<Mensagem, Long> {
	
	List<Mensagem> buscarMensagensParticipante(Participante participante);
	List<Mensagem> buscarMensagensDoJogo(Jogo jogo);
	
	int totalMensagens(Jogo jogo);
	
	List<ParticipanteMensagens> rankingEnviouMaisMensagens(Jogo jogo);
	
	List<ParticipanteMensagens> rankingRecebeuMaisMensagens(Jogo jogo);
}
