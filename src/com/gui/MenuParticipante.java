package com.gui;

import com.entidades.Jogo;
import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;
import com.servicos.ExcecaoValidacaoServico;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;
import com.servicos.ServicoMensagens;


public class MenuParticipante implements Menu {
	private Participante participanteAtual;
	private Participante amigoSecreto;
	private Jogo jogoAtual;
	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	private ServicoMensagens servicoMensagens;	
	
	public MenuParticipante(ContextoAmigoSecreto contexto, String nome_user) {
		if (contexto == null) {
			throw new IllegalArgumentException("Necessario passar o contexto da aplicacao");
		}
		
		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
		try {
			jogoAtual =  servicoJogos.buscarJogoAtual();
		} catch (ExcecaoValidacaoServico e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		participanteAtual = contexto.getRepositorioParticipantes().buscarParticipante(nome_user);
		amigoSecreto = contexto.getRepositorioParticipantes().buscarParticipante(nome_user);
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
			
			switch(opcao){
				case 0:			
					break;
				case 1:
					verAmigoAtual();
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					enviarMensagem();
					break;
				case 5:
					
					break;
				default:
					Escritor.EscreverLinha("Opção inválida");		
			}	
		}
	}
	public void verAmigoAtual() {
		Participante amigo = servicoJogos.buscarAmigoSecreto(jogoAtual, participanteAtual);
		Escritor.EscreverLinha("Seu amigo secreto atual é:");
		Escritor.EscreverLinha(amigo.getNome());
	}
	
	public void enviarMensagem() {
		Escritor.EscreverLinha("Digite a Mensagem");		
		String texto = Leitor.ler();
		try {
			servicoJogos.cadastrarMensagem(texto, participanteAtual.getCodinome(), amigoSecreto.getCodinome(), jogoAtual);
		} catch (ExcecaoValidacaoServico e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

}
