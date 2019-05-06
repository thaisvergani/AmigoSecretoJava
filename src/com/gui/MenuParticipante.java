package com.gui;

import com.identidade.ContextoAmigoSecreto;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;


public class MenuParticipante implements Menu {

	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	
	public MenuParticipante(ContextoAmigoSecreto contexto) {
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
			Escritor.EscreverLinha("1 - Ver Amigo Atual");
			Escritor.EscreverLinha("2 - Alterar Codinome");			
			Escritor.EscreverLinha("3 - Cadastrar Sugestões");			
			Escritor.EscreverLinha("4 - Enviar Mensagem");			
			Escritor.EscreverLinha("5 - Ler Mensagens");			
			Escritor.EscreverLinha("6 - Ler Avisos");
			Escritor.EscreverLinha("7 - Enviar Sugestão");			
			Escritor.EscreverLinha("8 - Demonstrativos dos jogos");			
		
			Escritor.EscreverLinha("0 - Sair");
			opcao = Leitor.lerInt();
			
		}
		
	}

}
