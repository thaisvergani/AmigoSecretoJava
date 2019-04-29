package com.servicos;

import java.util.List;

import com.entidades.Aviso;
import com.identidade.ContextoAmigoSecreto;

public class ServicoAvisos extends ServicoBase {

	public ServicoAvisos(ContextoAmigoSecreto contexto) {
		super(contexto);
	}
	
	public Aviso cadastrar(String mensagem) throws ExcecaoValidacaoServico {
		
		if (mensagem == null) {
			throw new ExcecaoValidacaoServico("Mensagem do aviso precisa ser informada");
		}
		
		Aviso aviso = new Aviso();
		aviso.setMensagem(mensagem);
		
		contexto.getRepositorioAvisos().adicionar(aviso);
		
		return aviso;
	}
	
	public List<Aviso> buscarTodos() {
		return contexto.getRepositorioAvisos().buscarTodosAvisos();
	}
	
}
