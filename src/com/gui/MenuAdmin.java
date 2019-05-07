package com.gui;

import java.util.Date;
import java.util.List;

import com.entidades.Aviso;
import com.entidades.Jogo;
import com.entidades.Participante;
import com.entidades.SugestaoAmigoSecreto;
import com.identidade.ContextoAmigoSecreto;
import com.servicos.ExcecaoValidacaoServico;
import com.servicos.ServicoAvisos;
import com.servicos.ServicoJogos;
import com.servicos.ServicoParticipantes;
import com.servicos.ServicoSugestoesJogo;


public class MenuAdmin implements Menu {

	private ServicoAvisos servicoAvisos;
	private ServicoJogos servicoJogos;
	private ServicoParticipantes servicoParticipantes;	
	private ServicoSugestoesJogo servicoSugestoesJogo;
	
	public MenuAdmin(ContextoAmigoSecreto contexto) {
		if (contexto == null) {
			throw new IllegalArgumentException("Necessario passar o contexto da aplicacao");
		}		

		servicoAvisos = new ServicoAvisos(contexto);
		servicoJogos = new ServicoJogos(contexto);
		servicoParticipantes = new ServicoParticipantes(contexto);
		servicoSugestoesJogo = new ServicoSugestoesJogo(contexto);
	}
	
	@Override
	public void entrar() {

		int opcao = -1;
		while (opcao != 0) {
			Console.escreverLinha("----- ADMINISTRADOR -----");
			Console.escreverLinha("1 - Listar participantes");
			Console.escreverLinha("2 - Listar avisos");			
			Console.escreverLinha("3 - Listar sugestoes");
			Console.escreverLinha("4 - Listar jogos");
			Console.escreverLinha("5 - Cadastrar participante");
			Console.escreverLinha("6 - Cadastrar aviso");
			Console.escreverLinha("7 - Sortear novo jogo");
			Console.escreverLinha("0 - Sair");
			opcao = Console.lerInt();

			try {
				if (opcao == 1) {
					listarParticipantes();
				} else if (opcao == 2) {
					listarAvisos();
				} else if (opcao == 3) {
					listarSugestoes();
				} else if (opcao == 4) {
					listarJogos();
				} else if (opcao == 5) {
					cadastrarParticipante();
				} else if (opcao == 6) {
					cadastrarAviso();
				} else if (opcao == 7) {
					sortearNovoJogo();
				}
			} catch (ExcecaoValidacaoServico e) {
				e.printStackTrace();
				Console.escreverLinha("Erro: " + e.getMessage());
			}			
		}
	}
	
	private void listarParticipantes() {
		List<Participante> participantes = servicoParticipantes.buscarTodos();
		if (participantes.size() == 0) {
			System.out.println("Nao existe nenhum participante.");
			return;
		}
		
		Console.escreverLinha("Escolha qual participante deseja acessar:");
		
		for (int i = 0; i < participantes.size(); i++) {
			Console.escreverLinha(i+1 + " - " + participantes.get(i).getNome());
		}
		
		Console.escreverLinha("0 - Voltar");
		
		int opcao = Console.lerInt();
		if (opcao == 0) return;
		
		opcao--;
		if (participantes.size() <= opcao) return;
		
		Participante jogo = participantes.get(opcao);
		// TODO Entrar no menu participante como Admin
	}
	
	private void listarAvisos() {
		List<Aviso> avisos = servicoAvisos.buscarTodos();
		if (avisos.size() == 0) {
			Console.escreverLinha("Sem nenhum aviso");
			return;
		}
		
		for (Aviso a : avisos) {
			Console.escreverLinha("- " + a.getMensagem());
		}
		
		Console.lerTeclaParaContinuar();
	}
	
	private void listarSugestoes() {
		List<SugestaoAmigoSecreto> sugestoes = servicoSugestoesJogo.buscarTodos();
		if (sugestoes.size() == 0) {
			Console.escreverLinha("Sem sugestoes registradas");
			return;
		}
		
		for (SugestaoAmigoSecreto s : sugestoes) {
			Console.escreverLinha("- " + s.getParticipante().getNome() + ": " + s.getDescricao());
		}
		
		Console.lerTeclaParaContinuar();
	}
	
	private void listarJogos() {
		
		List<Jogo> jogos = servicoJogos.buscarTodosJogos();
		if (jogos.size() == 0) {
			System.out.println("Nao existe nenhum jogo.");
			return;
		}
		
		Console.escreverLinha("Escolha qual jogo deseja acessar:");
		
		for (int i = 0; i < jogos.size(); i++) {
			Console.escreverLinha(i+1 + " - " + jogos.get(i).getNome());
		}
		
		Console.escreverLinha("0 - Voltar");
		
		int opcao = Console.lerInt();
		if (opcao == 0) return;
		
		opcao--;
		if (jogos.size() <= opcao) return;
		
		Jogo jogo = jogos.get(opcao);
		new MenuJogo(jogo, servicoJogos, servicoParticipantes).entrar();
	}
	
	private void cadastrarParticipante() throws ExcecaoValidacaoServico {
		
		Console.escrever("Escreva o nome do participante: ");
		String nome = Console.lerLinha();
		
		Console.escrever("Escreva o codinome do participante: ");
		String codinome = Console.lerLinha();
		
		servicoParticipantes.adicionar(nome, codinome);
		Console.escreverLinha("Participante criado com sucesso");
	}
	
	private void cadastrarAviso() throws ExcecaoValidacaoServico {
		Console.escrever("Escreva o aviso que deseja registrar:");
		String mensagem = Console.lerLinha();
		servicoAvisos.cadastrar(mensagem);
		Console.escreverLinha("Aviso registrado com sucesso");
	}
	
	private void sortearNovoJogo() throws ExcecaoValidacaoServico {
		
		Console.escrever("Insira a data final do jogo (dd-MM-yyyy): ");
		Date data = Console.lerDate();
		
		Jogo jogo = servicoJogos.criarNovo(data);
		Console.escreverLinha("Jogo '" + jogo.getNome() + "' criado com sucesso");
		
	}
}
