package com.gui;

import com.identidade.ContextoAmigoSecreto;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;


public class MenuAdmin implements Menu {

	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	private ServicoParticipantes servicoMensagen;	
	
	public MenuAdmin(ContextoAmigoSecreto contexto) {
		if (contexto == null) {
			throw new IllegalArgumentException("Necessario passar o contexto da aplicacao");
		}
		
		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
	}
	
	@Override
	public void entrar() {

		int opcao = -1;
		while (opcao != 0) {
			Escritor.EscreverLinha("1 - Cadastrar Participante");
			Escritor.EscreverLinha("2 - Listar Participantes");			
			Escritor.EscreverLinha("3 - Sortear Novo Jogo");			
			Escritor.EscreverLinha("4 - Cadastrar Mensagens");			
			Escritor.EscreverLinha("5 - Listar Mensagens");			
			Escritor.EscreverLinha("6 - Cadastrar Sugestões de Presente");			
			Escritor.EscreverLinha("7 - Listar Sugestões de Presente de um Participante");			
			Escritor.EscreverLinha("8 - Cadastrar Sugestões Gerais");			
			Escritor.EscreverLinha("9 - Listar Sugestões Gerais");			
			Escritor.EscreverLinha("10 - Cadastrar Avisos Gerais");			
			Escritor.EscreverLinha("11 - Listar Avisos Gerais");	
			Escritor.EscreverLinha("12 - Relatórios de Mensagens Enviadas e Recebidas");			
			Escritor.EscreverLinha("13 - Relatórios de Amizades");			
			Escritor.EscreverLinha("0 - Sair");

			opcao = Leitor.lerInt();
			
		}
		
	}

}
