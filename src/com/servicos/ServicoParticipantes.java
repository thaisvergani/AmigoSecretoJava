package com.servicos;

import java.util.List;

import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;

public class ServicoParticipantes extends ServicoBase {

	public ServicoParticipantes(ContextoAmigoSecreto contexto) {
		super(contexto);
	}
	
	public Participante adicionar(String nome, String codinome, int ramal) throws ExcecaoValidacaoServico {
		if (nome == null) {
			throw new ExcecaoValidacaoServico("Nome do participante precisa ser informado");
		}
		
		if (codinome == null) {
			throw new ExcecaoValidacaoServico("Codinome do participante precisa ser informado");
		}
		
		Participante participante = new Participante();
		participante.setNome(nome);
		participante.setCodinome(codinome);
		participante.setRamal(ramal);
		
		contexto.getRepositorioParticipantes().adicionar(participante);
		
		return participante;
	}
	
	public Participante atualizar(Long id, String nome, String codinome, int ramal) throws ExcecaoValidacaoServico {
		if (nome == null) {
			throw new ExcecaoValidacaoServico("Nome do participante precisa ser informado");
		}
		
		if (codinome == null) {
			throw new ExcecaoValidacaoServico("Codinome do participante precisa ser informado");
		}
		
		Participante participante = contexto.getRepositorioParticipantes().buscaPorId(id);
		if (participante == null) {
			throw new ExcecaoValidacaoServico("Participante nao encontrado");
		}
		
		participante.setNome(nome);
		participante.setCodinome(codinome);
		participante.setRamal(ramal);
		
		contexto.getRepositorioParticipantes().atualizar(participante);
		
		return participante;
	}

	public List<Participante> buscarTodos() {
		return contexto.getRepositorioParticipantes().buscarTodosParticipantes();
	}	
	
}
