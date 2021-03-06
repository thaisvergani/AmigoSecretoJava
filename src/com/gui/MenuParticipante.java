package com.gui;

import java.util.List;

import com.entidades.Amizade;
import com.entidades.Aviso;
import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;
import com.identidade.ContextoAmigoSecreto;
import com.servicos.ExcecaoValidacaoServico;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;
import com.servicos.ServicoSugestoesJogo;
import com.servicos.ServicoSugestoesPresente;
import com.servicos.ServicoMensagens;


public class MenuParticipante implements Menu {
	public Participante participanteAtual;
	public Participante getParticipanteAtual() {
		return participanteAtual;
	}

	private Participante amigoSecreto;
	private Jogo jogoAtual;
	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	private ServicoMensagens servicoMensagens;	
	private ServicoSugestoesJogo servicoSugestaoesJogo;
	private ServicoSugestoesPresente servicoSugestoesPresente;
	
	public MenuParticipante(ContextoAmigoSecreto contexto, String nome_user) {
		if (contexto == null) {
			throw new IllegalArgumentException("Necessario passar o contexto da aplicacao");
		}

		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
		servicoMensagens = new ServicoMensagens(contexto);
		servicoSugestaoesJogo = new ServicoSugestoesJogo(contexto);
		servicoSugestoesPresente = new ServicoSugestoesPresente(contexto);
		
		try {
			jogoAtual = servicoJogos.buscarJogoAtual();
		} catch (ExcecaoValidacaoServico e) {
			e.printStackTrace();
		}
		participanteAtual = contexto.getRepositorioParticipantes().buscarParticipante(nome_user);

		amigoSecreto = servicoJogos.buscarAmigoSecreto(jogoAtual, participanteAtual);
	}
	
	@Override
	public void entrar() {

		int opcao = -1;
		while (opcao != 0) {
			Console.escreverLinha("1 - Ver Amigo Atual");
			Console.escreverLinha("2 - Alterar Codinome");			
			Console.escreverLinha("3 - Cadastrar Sugestoes");			
			Console.escreverLinha("4 - Enviar Mensagem");			
			Console.escreverLinha("5 - Ler Mensagens");			
			Console.escreverLinha("6 - Ler Avisos");
			Console.escreverLinha("7 - Enviar Sugestao");			
			Console.escreverLinha("8 - Demonstrativos dos jogos");			
		
			Console.escreverLinha("0 - Sair");
			opcao = Console.lerInt();
			try {

				switch(opcao){
					case 0:			
						break;
					case 1:
						verAmigoAtual();
						break;
					case 2:
						alterarCodinome();
						break;
					case 3:
						enviarSugestaoPresente();
						break;
					case 4:
						enviarMensagem();					
						break;
					case 5:
						lerMensagens();
						break;
					case 6:
						lerAvisos();
						break;
					case 7:
						enviarSugestao();
						break;
					case 8:
						demonstrativos();
						break;
					default:
						Console.escreverLinha("Opcao invalida");		
	
				}	
			} catch (ExcecaoValidacaoServico e) {
				e.printStackTrace();
				Console.escreverLinha(e.getMessage());
			}
		}
	}
	
	public void verAmigoAtual() {
		Console.escreverLinha("Seu amigo secreto atual:");
				//todo mostrar a sugestao de presente do amigo
		Console.escreverLinha(amigoSecreto.getNome());
	}
	
	public void alterarCodinome() throws ExcecaoValidacaoServico {
		Console.escreverLinha("Informe seu novo codinome:");
		String novo_codinome = Console.ler();

		Participante participanteAtualizado = servicoParticipantes.atualizar(
				participanteAtual.id,
				participanteAtual.getNome(),
				novo_codinome);
		participanteAtual = participanteAtualizado;
	}
	
	public void enviarSugestao() throws ExcecaoValidacaoServico {
		Console.escreverLinha("Deixe sua sugestao para o jogo:");
		String mensagem = Console.ler();

		servicoSugestaoesJogo.cadastrar(participanteAtual, mensagem);
	}
	
	public void enviarSugestaoPresente() throws ExcecaoValidacaoServico {
		Console.escreverLinha("Informe sua sugestao de presente:");
		String sugestao = Console.lerLinha();
		servicoSugestoesPresente.cadastrar(jogoAtual, participanteAtual, sugestao);
	}
	
	public void lerMensagens() throws ExcecaoValidacaoServico {
		List<Mensagem> mensagens = servicoMensagens.buscarMensagensParticipante( participanteAtual);		
		for (Mensagem m : mensagens) {
			System.out.println(m.getRemetente().getCodinome() + " - " + m.getData() + " - " + m.getTexto());
		}
	}
	
	public void lerAvisos() throws ExcecaoValidacaoServico {
		List<Aviso> avisos = servicoAvisos.buscarTodos();					
		for (Aviso a : avisos) {
			System.out.println(a.getId() + " - " + a.getMensagem());
		}
	}
	
	public void enviarMensagem() throws ExcecaoValidacaoServico {
		Console.escreverLinha("Digite a Mensagem a ser enviada para seu amigo secreto");		
		String texto = Console.lerLinha();
		servicoJogos.cadastrarMensagemParticipante(texto, participanteAtual, amigoSecreto, jogoAtual);
	
	
		
	}
	public void demonstrativos( ) throws ExcecaoValidacaoServico {
		List<Jogo> jogos = servicoJogos.buscarJogosEncerrados();
		for (Jogo j : jogos) {
			
			Console.escreverLinha("Jogo:" + j.getNome());	
			Console.escreverLinha("\t Total de mensagens:" );	
			Console.escreverLinha("\t Ranking dos participantes que mais receberam mensagens:");	
			List<Participante> mais_receberam = servicoJogos.rankingRecebeuMaisMensagens(j);		
			for (Participante p : mais_receberam) {
				Console.escreverLinha(p.getNome());	
			}
			Console.escreverLinha("\t Ranking dos participantes que mais enviaram mensagens:");	
			List<Participante> mais_enviaram = servicoJogos.rankingEnviouMaisMensagens(j);		
			for (Participante p : mais_enviaram) {
				Console.escreverLinha(p.getNome());	
			}
//			Console.escreverLinha("\t Lista dos codinomes, com seus respectivos participantes:");	
			Console.escreverLinha("\t Lista de quem tirou quem:");	
			List<Amizade> amizades = servicoJogos.buscarAmizadesDoJogo(j);
			for (Amizade a : amizades) {
				Console.escreverLinha(
					a.getParticipante().getNome() + "(" + a.getParticipante().getCodinome() + ") -> " + 
					a.getAmigoSecreto().getNome() + "(" + a.getAmigoSecreto().getCodinome() + ")");
			}
		}
	}

}
