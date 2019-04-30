package com.gui;

import com.identidade.ContextoAmigoSecreto;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;


public class MenuInicial implements Menu {

	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	
	public MenuInicial(ContextoAmigoSecreto contexto) {
		if (contexto == null) {
			throw new IllegalArgumentException("Necessario passar o contexto da aplicacao");
		}
		
		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
	}
	
	@Override
	public void entrar() {

		int opcao = -1;
		while (opcao != 0) {
			Escritor.EscreverLinha("1 - Participantes");
			Escritor.EscreverLinha("2 - Jogos");			
			Escritor.EscreverLinha("0 - Sair");

			opcao = Leitor.lerInt();

			
		}
		
	}

}
