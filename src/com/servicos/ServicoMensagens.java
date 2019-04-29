package com.servicos;

import java.util.Date;
import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;
import com.identidade.ContextoAmigoSecreto;

public class ServicoMensagens extends ServicoBase {

	public ServicoMensagens(ContextoAmigoSecreto contexto) {
		super(contexto);
	}
	
	public List<Mensagem> buscarMensagensDoJogo(Jogo jogo) {
		return contexto.getRepositorioMensagens().buscarMensagensDoJogo(jogo);
	}
	
	public List<ParticipanteMensagens> rankingRecebeuMaisMensagens(Jogo jogo) {
		return contexto.getRepositorioMensagens().rankingRecebeuMaisMensagens(jogo);
	}

	public List<ParticipanteMensagens> rankingEnviouMaisMensagens(Jogo jogo) {
		return contexto.getRepositorioMensagens().rankingEnviouMaisMensagens(jogo);
	}
	
	public Mensagem cadastrarMensagem(String texto, String codinomeOrigem, String codinomeDestino, Jogo jogo) throws ExcecaoValidacaoServico {
		if (texto == null) {
			throw new ExcecaoValidacaoServico("Necessario informar o texto que deseja enviar na mensagem");
		}
		
		Participante remetente = contexto.getRepositorioParticipantes().buscarParticipante(codinomeOrigem);
		if (remetente == null) {
			throw new ExcecaoValidacaoServico("Nao foi possivel encontrar o participante do codinome: " + codinomeOrigem);
		}
				
		Participante destinatario = contexto.getRepositorioParticipantes().buscarParticipante(codinomeDestino);
		if (destinatario == null) {
			throw new ExcecaoValidacaoServico("Nao foi possivel encontrar o participante do codinome: " + codinomeDestino);
		}
		
		Mensagem mensagem = new Mensagem(texto, new Date(), remetente, destinatario, jogo);
		contexto.getRepositorioMensagens().adicionar(mensagem);
		return mensagem;
	}
	
}
