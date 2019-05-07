package com.gui;

import java.util.List;

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
	private Participante participanteAtual;
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
		
		try {
			jogoAtual = servicoJogos.buscarJogoAtual();
		} catch (ExcecaoValidacaoServico e) {
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
			Escritor.EscreverLinha("3 - Cadastrar Sugestões de presente");			
			Escritor.EscreverLinha("4 - Enviar Mensagem");			
			Escritor.EscreverLinha("5 - Ler Mensagens");			
			Escritor.EscreverLinha("6 - Ler Avisos");
			Escritor.EscreverLinha("7 - Enviar Sugestão para os jogos");			
			Escritor.EscreverLinha("8 - Demonstrativos dos jogos");			
		
			Escritor.EscreverLinha("0 - Sair");
			opcao = Leitor.lerInt();
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
						enviarSugestaoJogo();
						break;
					case 8:
						demonstrativos();
						break;
					default:
						Escritor.EscreverLinha("Opção inválida");		
	
				}	
			} catch (ExcecaoValidacaoServico e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void verAmigoAtual() {
		Participante amigo = servicoJogos.buscarAmigoSecreto(jogoAtual, participanteAtual);
		Escritor.EscreverLinha("Seu amigo secreto atual é:");
		//todo mostrar a sugestao de presente do amigo
		Escritor.EscreverLinha(amigo.getNome());
	}
	public void alterarCodinome() throws ExcecaoValidacaoServico {
		Escritor.EscreverLinha("Informe seu novo codinome:");
		String novo_codinome = Leitor.ler();

		Participante participanteAtualizado = servicoParticipantes.atualizar(
				participanteAtual.getId(),
				participanteAtual.getNome(),
				novo_codinome, 
				0);
		participanteAtual = participanteAtualizado;
	}
	public void enviarSugestaoJogo() throws ExcecaoValidacaoServico {
		Escritor.EscreverLinha("Deixe sua sugestão para o jogo:");
		String mensagem = Leitor.ler();
		servicoSugestaoesJogo.cadastrar(participanteAtual, mensagem);
	}
	
	public void enviarSugestaoPresente() throws ExcecaoValidacaoServico {
		Escritor.EscreverLinha("Informe sua sugestão de presente:");
		String sugestao = Leitor.ler();
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
		Escritor.EscreverLinha("Digite a Mensagem a ser enviada para seu amigo secreto");		
		String texto = Leitor.ler();
		servicoJogos.cadastrarMensagem(texto, participanteAtual.getCodinome(), amigoSecreto.getCodinome(), jogoAtual);		
	}
	public void demonstrativos( ) throws ExcecaoValidacaoServico {
		List<Jogo> jogos = servicoJogos.buscarJogosEncerrados();
		for (Jogo j : jogos) {
			
			Escritor.EscreverLinha("Jogo:" + j.getNome());	
			Escritor.EscreverLinha("\t Total de mensagens:" );	
			Escritor.EscreverLinha("\t Ranking dos participantes que mais receberam mensagens:");	
			List<ParticipanteMensagens> mais_receberam = servicoJogos.rankingRecebeuMaisMensagens(j);		
			for (ParticipanteMensagens p : mais_receberam) {
				Escritor.EscreverLinha(p.getNome());	
			}
			Escritor.EscreverLinha("\t Ranking dos participantes que mais enviaram mensagens:");	
			List<ParticipanteMensagens> mais_enviaram = servicoJogos.rankingEnviouMaisMensagens(j);		
			for (ParticipanteMensagens p : mais_enviaram) {
				Escritor.EscreverLinha(p.getNome());	
			}
//			Escritor.EscreverLinha("\t Lista dos codinomes, com seus respectivos participantes:");	
//			Escritor.EscreverLinha("\t Lista de quem tirou quem:");	
		}
	}

}
