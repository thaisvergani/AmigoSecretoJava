package com.testes;

import java.util.List;

import com.dao.ContextoDAOAmigoSecreto;
import com.entidades.Aviso;
import com.identidade.ContextoAmigoSecreto;
import com.servicos.ExcecaoValidacaoServico;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;

public class TesteJPA {

	private static ServicoAvisos servicoAvisos;
	private static ServicoJogos servicoJogos;
	private static ServicoParticipantes servicoParticipantes;
	
	
	public static void main(String[] args) {
		
		boolean testesFuncionaram;
	
		try {
			init();
			testarAvisos();	
			
			
			testesFuncionaram = true;
		} catch (Exception e) {
			e.printStackTrace();
			testesFuncionaram = false;
		}
		
		
		if (testesFuncionaram) {
			System.out.println("Testes funcionaram");
		} else {
			System.out.println("Testes nao funcionaram");
		}
	}
	
	private static void init() {
		ContextoAmigoSecreto contexto = new ContextoDAOAmigoSecreto();
		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
	}
	
	private static void testarAvisos() throws ExcecaoValidacaoServico {
		
		List<Aviso> avisos = servicoAvisos.buscarTodos();
			
		for (Aviso a : avisos) {
			
		}
		
		servicoAvisos.cadastrar("A partir de julho nao teremos mais aula!");
		
		servicoAvisos.cadastrar("Porque vamos estar formados! :)");
		
		avisos = servicoAvisos.buscarTodos();
		
		for (Aviso a : avisos) {
			System.out.println(a.getId() + " - " + a.getMensagem());
		}
	}

}
