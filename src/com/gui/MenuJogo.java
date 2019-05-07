package com.gui;

import java.text.SimpleDateFormat;
import java.util.List;

import com.entidades.Amizade;
import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;
import com.servicos.ExcecaoValidacaoServico;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;

public class MenuJogo implements Menu {

	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;
	private Jogo jogo;
	
	public MenuJogo(Jogo jogo, ServicoJogos servicoJogos, ServicoParticipantes servicoParticipantes) {
		if (jogo == null) {
			throw new IllegalArgumentException("Necessario informar o jogo que deseja acessar");
		}
		
		if (servicoJogos == null) {
			throw new IllegalArgumentException("Necessario informar o servico dos jogos");
		}
		
		if (servicoParticipantes == null) {
			throw new IllegalArgumentException("Necessario informar o servico dos participantes");
		}
		
		this.jogo = jogo;
		this.servicoJogos = servicoJogos;
		this.servicoParticipantes = servicoParticipantes;
	}

	@Override
	public void entrar() {
		
		int opcao = -1;
		while (opcao != 0) {
			Console.escreverLinha("----- JOGO " + jogo.getNome() + " -----");
			Console.escreverLinha("1 - Visualizar amizades");
			Console.escreverLinha("2 - Visualizar todas mensagens");
			Console.escreverLinha("3 - Cadastrar mensagem");
			Console.escreverLinha("4 - Ranking mensagens enviadas");
			Console.escreverLinha("5 - Ranking mensagens recebidas");
			Console.escreverLinha("0 - Voltar");

			opcao = Console.lerInt();
			
			try {
				if (opcao == 1) {
					visualizarAmizades();
				} else if (opcao == 2) {
					visualizarMensagens();
				} else if (opcao == 3) {
					cadastrarMensagem();
				} else if (opcao == 4) {
					rankingMensagensEnviadas();
				} else if (opcao == 5) {
					rankingMensagensRecebidas();
				}
			} catch (ExcecaoValidacaoServico e) {
				e.printStackTrace();
				Console.escreverLinha(e.getMessage());
			}
		}
		
	}
	
	private void visualizarAmizades() {
		List<Amizade> amizades = servicoJogos.buscarAmizadesDoJogo(this.jogo);
		for (Amizade a : amizades) {
			Console.escreverLinha(
				a.getParticipante().getNome() + "(" + a.getParticipante().getCodinome() + ") -> " + 
				a.getAmigoSecreto().getNome() + "(" + a.getAmigoSecreto().getCodinome() + ")");
		}
	}
	
	private void visualizarMensagens() {
		List<Mensagem> mensagens = servicoJogos.buscarMensagensDoJogo(this.jogo);
		if (mensagens.size() == 0) {
			Console.escreverLinha("Sem mensagens");
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM hh:mm");
		
		for (Mensagem m : mensagens) {
			Console.escreverLinha(
				m.getRemetente().getNome() + " -> " + 
				m.getDestinatario().getNome() + 
				" (" + format.format(m.getData()) + "): " + 
				m.getTexto());
		}
	}
	
	private void cadastrarMensagem() throws ExcecaoValidacaoServico {
		
		List<Participante> participantes = servicoParticipantes.buscarTodos();
		
		Console.escreverLinha("Codinomes disponveis:");
		for (Participante p : participantes) {
			Console.escreverLinha(p.getCodinome());
		}

		Console.escrever("Escreva o codinome do participante remetente: ");
		String remetente = Console.lerLinha();
		
		Console.escrever("Escreva o codinome do participante destinatario: ");
		String destinatario = Console.lerLinha();
		
		Console.escrever("Escreva a mensagem que deseja enviar: ");
		String texto = Console.lerLinha();
		
		servicoJogos.cadastrarMensagem(texto, remetente, destinatario, jogo);
		Console.escrever("Mensagem enviada com sucesso");
	}
	
	private void rankingMensagensEnviadas() {
		List<ParticipanteMensagens> participantes = servicoJogos.rankingRecebeuMaisMensagens(this.jogo);
		if (participantes.size() == 0) {
			Console.escreverLinha("Sem mensagens");
			return;
		}

		Console.escreverLinha("Ranking mensagens enviadas:");
		int posicao = 0;
		for (ParticipanteMensagens p : participantes) {
			posicao++;
			Console.escreverLinha(posicao + " - " + p.getNome() + ": " + p.getTotalMensagens() + " mensagens");
		}
	}
	
	private void rankingMensagensRecebidas() {
		List<ParticipanteMensagens> participantes = servicoJogos.rankingRecebeuMaisMensagens(this.jogo);
		if (participantes.size() == 0) {
			Console.escreverLinha("Sem mensagens");
			return;
		}
		
		Console.escreverLinha("Ranking mensagens recebidas:");
		int posicao = 0;
		for (ParticipanteMensagens p : participantes) {
			posicao++;
			Console.escreverLinha(posicao + " - " + p.getNome() + ": " + p.getTotalMensagens() + " mensagens");
		}
	}

}
