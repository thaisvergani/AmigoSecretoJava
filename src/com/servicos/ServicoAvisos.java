package com.servicos;

import java.util.List;

import com.entidades.Aviso;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioAvisos;

public class ServicoAvisos extends ServicoBase<Aviso, Long> {

	public ServicoAvisos(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioAvisos());
	}
	
	public Aviso cadastrar(String mensagem) throws ExcecaoValidacaoServico {
		
		if (mensagem == null) {
			throw new ExcecaoValidacaoServico("Mensagem do aviso precisa ser informada");
		}
		
		Aviso aviso = new Aviso();
		aviso.setMensagem(mensagem);
		
		repositorio.adicionar(aviso);
		
		return aviso;
	}
	
	public List<Aviso> buscarTodos() {
		return ((RepositorioAvisos)repositorio).buscarTodosAvisos();
	}	
}
