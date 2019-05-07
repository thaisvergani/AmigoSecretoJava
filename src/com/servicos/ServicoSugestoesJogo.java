package com.servicos;

import java.util.List;

import com.entidades.Participante;
import com.entidades.SugestaoAmigoSecreto;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioAvisos;
import com.identidade.RepositorioSugestoesJogos;

public class ServicoSugestoesJogo extends ServicoBase<SugestaoAmigoSecreto, Long> {

	public ServicoSugestoesJogo(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioSugestoesJogos());
	}
	
	public SugestaoAmigoSecreto cadastrar(Participante participante, String mensagem) throws ExcecaoValidacaoServico {
		
		if (mensagem == null) {
			throw new ExcecaoValidacaoServico("Mensagem da sugestao precisa ser informada");
		}
		
		SugestaoAmigoSecreto s = new SugestaoAmigoSecreto(participante, mensagem);
		
		repositorio.adicionar(s);
		
		return s;
	}
	
	public List<SugestaoAmigoSecreto> buscarTodos() {
		return ((RepositorioSugestoesJogos)repositorio).buscarTodasSugestoes();
	}	
}
