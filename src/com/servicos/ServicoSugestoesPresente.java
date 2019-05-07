package com.servicos;

import java.util.List;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.SugestaoPresente;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioSugestoesPresentes;

public class ServicoSugestoesPresente extends ServicoBase<SugestaoPresente, Long> {

	public ServicoSugestoesPresente(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioSugestoesPresentes());
	}
	
	public SugestaoPresente cadastrar(Jogo jogo, Participante participante, String mensagem) throws ExcecaoValidacaoServico {
		
		if (mensagem == null) {
			throw new ExcecaoValidacaoServico("Sugestao precisa ser informada");
		}
		
		SugestaoPresente s = new SugestaoPresente(jogo, participante, mensagem);
		
		repositorio.adicionar(s);
		
		return s;
	}
	
	public List<SugestaoPresente> buscarTodos() {
		return ((RepositorioSugestoesPresentes)repositorio).buscarTodasSugestoes();
	}	
}
