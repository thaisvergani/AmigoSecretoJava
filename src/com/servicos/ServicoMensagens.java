package com.servicos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioMensagens;

public class ServicoMensagens extends ServicoBase<Mensagem, Long> {

	public ServicoMensagens(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioMensagens());
	}
	
	public Mensagem cadastrar(String texto, Participante remetente, Participante destinatario, Jogo jogo) throws ExcecaoValidacaoServico {
		
		if (texto == null) {
			throw new ExcecaoValidacaoServico("Mensagem do aviso precisa ser informada");
		}
		Date date = new Date();
		Mensagem msg = new Mensagem(texto, date,  remetente,  destinatario,  jogo) ;
		
		repositorio.adicionar(msg);
		
		return msg;
	}
	
	public List<Mensagem> buscarTodos() {
		
		return ((RepositorioMensagens)repositorio).buscarMensagensDoJogo(null);
	}	
}
