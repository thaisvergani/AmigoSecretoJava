package com.servicos;

import java.util.List;

import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioParticipantes;

public class ServicoParticipantes extends ServicoBase<Participante, Long> {

	public ServicoParticipantes(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioParticipantes());
	}
	
	public Participante adicionar(String nome, String codinome) throws ExcecaoValidacaoServico {
		if (nome == null) {
			throw new ExcecaoValidacaoServico("Nome do participante precisa ser informado");
		}
		
		if (codinome == null) {
			throw new ExcecaoValidacaoServico("Codinome do participante precisa ser informado");
		}
		
		Participante participante = new Participante();
		participante.setNome(nome);
		participante.setCodinome(codinome);
		
		repositorio.adicionar(participante);
		
		return participante;
	}
	
	public Participante atualizar(Long id, String nome, String codinome) throws ExcecaoValidacaoServico {
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
		
		repositorio.atualizar(participante);
		
		return participante;
	}
	
	public Participante buscarPeloCodinome(String codinome) {
		return ((RepositorioParticipantes)repositorio).buscarParticipante(codinome);
	}

	public List<Participante> buscarTodos() {
		return ((RepositorioParticipantes)repositorio).buscarTodosParticipantes();
	}	
	
}
