package com.servicos;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.entidades.Amizade;
import com.entidades.Jogo;
import com.entidades.Mensagem;
import com.entidades.Participante;
import com.entidades.ParticipanteMensagens;
import com.entidades.SugestaoAmigoSecreto;
import com.entidades.SugestaoPresente;
import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioJogos;

public class ServicoJogos extends ServicoBase<Jogo, Long> {

	public ServicoJogos(ContextoAmigoSecreto contexto) {
		super(contexto, contexto.getRepositorioJogos());
	}
	
	public Jogo criarNovo(Date fim) throws ExcecaoValidacaoServico {
		return criarNovo(fim, null);
	}
	
	public Jogo buscarJogoAtual()  throws ExcecaoValidacaoServico {
		List<Jogo> ultimosJogos = ((RepositorioJogos)repositorio).buscarUltimosJogos(1);
		return ultimosJogos.get(0);
	}
	
	public List<Jogo> buscarJogosEncerrados()  throws ExcecaoValidacaoServico {
		List<Jogo> jogosEncerrados = ((RepositorioJogos)repositorio).buscarJogosEncerrados();
		return jogosEncerrados;
	}
	
	public Jogo criarNovo(Date fim, String nome) throws ExcecaoValidacaoServico {
		List<Participante> participantes = contexto.getRepositorioParticipantes().buscarTodosParticipantes();
		if (participantes.size() < 3) {
			throw new ExcecaoValidacaoServico("Necessario possuir pelo menos 3 participantes para criar um amigo secreto");
		}
		
		if (nome == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			nome = "Amigo Secreto dia " +  formatter.format(fim);			
		}
		
		List<Jogo> ultimosJogos = ((RepositorioJogos)repositorio).buscarUltimosJogos(4);
		
		Jogo jogo = new Jogo();		
		jogo.setNome(nome);
		jogo.setInicio(new Date());
		jogo.setFim(fim);
		
		repositorio.adicionar(jogo);
		
		List<Amizade> amizadesJogo = sorteio(jogo, participantes, ultimosJogos);
				
		((RepositorioJogos)repositorio).persistirAmizadesDoJogo(amizadesJogo);
		
		return jogo;
	}
	
	public List<Jogo> buscarTodosJogos() {
		return ((RepositorioJogos)repositorio).buscarTodosJogos();
	}
	
	public List<Amizade> buscarAmizadesDoJogo(Jogo jogo) {
		return ((RepositorioJogos)repositorio).buscarAmizadesDoJogo(jogo);
	}
	
	public SugestaoPresente cadastrarSugestaoPresente(Jogo jogo, Participante participante, String descricao) throws ExcecaoValidacaoServico {
		if (jogo == null) {
			throw new ExcecaoValidacaoServico("Necessario informar qual o jogo que essa sugestao pertence");
		}
		
		if (participante == null) {
			throw new ExcecaoValidacaoServico("Necessario informar qual o participante que fez a sugestao");
		}
		
		if (descricao == null || descricao == "") {
			throw new ExcecaoValidacaoServico("Necessario informar a descricao do presente que deseja sugerir");
		}
		
		
		SugestaoPresente sugestao = new SugestaoPresente(jogo, participante, descricao);
		contexto.getRepositorioSugestoesPresentes().adicionar(sugestao);
		return sugestao;
	}
	
	public List<SugestaoPresente> buscarSugestaoPresentes(Participante participante, Jogo jogo) {
		return contexto.getRepositorioSugestoesPresentes().buscarSugestoes(participante, jogo);
	}
	
	public SugestaoAmigoSecreto cadastrarSugestaoJogo(Participante participante, String mensagem) throws ExcecaoValidacaoServico {
		if (participante == null) {
			throw new ExcecaoValidacaoServico("Necessario informar qual o participante que fez a sugestao");
		}
		
		if (mensagem == null || mensagem == "") {
			throw new ExcecaoValidacaoServico("Necessario informar a mensagem que deseja sugerir para os proximos jogos");
		}
		
		SugestaoAmigoSecreto sugestao = new SugestaoAmigoSecreto(participante, mensagem);
		contexto.getRepositorioSugestoesJogos().adicionar(sugestao);
		return sugestao;
	}
	
	public List<SugestaoAmigoSecreto> buscarTodasSugestoesJogo() {
		return contexto.getRepositorioSugestoesJogos().buscarTodasSugestoes();
	}
	
	public List<Mensagem> buscarMensagensDoJogo(Jogo jogo) {
		return contexto.getRepositorioMensagens().buscarMensagensDoJogo(jogo);
	}
	
	public List<Participante> rankingRecebeuMaisMensagens(Jogo jogo) {
		return contexto.getRepositorioMensagens().rankingRecebeuMaisMensagens(jogo);
	}

	public List<Participante> rankingEnviouMaisMensagens(Jogo jogo) {
		return contexto.getRepositorioMensagens().rankingEnviouMaisMensagens(jogo);
	}
	
	public Mensagem cadastrarMensagem(String texto, String codinomeOrigem, String codinomeDestino, Jogo jogo) throws ExcecaoValidacaoServico {
		if (texto == null) {
			throw new ExcecaoValidacaoServico("Necessario informar o texto que deseja enviar na mensagem");
		}
		
		Participante remetente = contexto.getRepositorioParticipantes().buscarParticipante(codinomeOrigem);
		if (remetente == null) {
			throw new ExcecaoValidacaoServico("Nao foi possivel encontrar o participante do codinome: " + codinomeOrigem);
		}
				
		Participante destinatario = contexto.getRepositorioParticipantes().buscarParticipante(codinomeDestino);
		if (destinatario == null) {
			throw new ExcecaoValidacaoServico("Nao foi possivel encontrar o participante do codinome: " + codinomeDestino);
		}
		
		Mensagem mensagem = new Mensagem(texto, new Date(), remetente, destinatario, jogo);
		contexto.getRepositorioMensagens().adicionar(mensagem);
		return mensagem;
	}
	
	public Mensagem cadastrarMensagemParticipante(String texto, Participante remetente, Participante destinatario, Jogo jogo) throws ExcecaoValidacaoServico {
		if (texto == null) {
			throw new ExcecaoValidacaoServico("Necessario informar o texto que deseja enviar na mensagem");
		}
		
		
		Mensagem mensagem = new Mensagem(texto, new Date(), remetente, destinatario, jogo);
		contexto.getRepositorioMensagens().adicionar(mensagem);
		return mensagem;
	}
	
	private List<Amizade> sorteio(Jogo jogo, List<Participante> participantes, List<Jogo> ultimosJogos) {
		
		// Se tivermos mais jogos do que participantes nao tem como evitar a repeticao de amizades 
		while (participantes.size() - 2 < ultimosJogos.size()) {
			ultimosJogos.remove(ultimosJogos.size() - 1);
		}
		
		Collections.shuffle(participantes);
		
		List<Amizade> amizades = new ArrayList<Amizade>(participantes.size());
		Map<Long, List<Long>> amizadesProibidas = new HashMap<Long, List<Long>>();
		
		for (Participante p : participantes) {			
			
			//byte[] array = new byte[7]; // length is bounded by 7
		    //new Random().nextBytes(array);
		    //String codinome = new String(array, Charset.forName("UTF-8"));
		    //p.setCodinome(codinome); 
		    //contexto.getRepositorioParticipantes().atualizar(p);
			
			amizades.add(new Amizade(p, jogo));
			
			List<Long> lista = new ArrayList<Long>();
			lista.add(p.getId());
			amizadesProibidas.put(p.getId(), lista);
		}				
		
		for (Jogo j : ultimosJogos) {
			for (Amizade a : ((RepositorioJogos)repositorio).buscarAmizadesDoJogo(j)) {
				Long idParticipante = a.getParticipante().getId();
				List<Long> amizadesSecretasProibidas = amizadesProibidas.get(idParticipante);
				amizadesSecretasProibidas.add(a.getAmigoSecreto().getId());
			}
		}
		
		
		
		for (Amizade amizade : amizades) {
			Long participanteId = amizade.getParticipante().getId();
			List<Long> participantesProibidos = amizadesProibidas.get(participanteId);
			
			Participante amigoSecreto = null;
						
			Random random = new Random(participantes.size() * 10);
			do {
				int indice = random.nextInt(participantes.size());
				amigoSecreto = participantes.get(indice);			
			} while (participantesProibidos.contains(amigoSecreto.getId()));
			
			amizade.setAmigoSecreto(amigoSecreto);
			participantes.remove(amigoSecreto);
		}		
		
		return amizades;
	}
	
	public Participante buscarAmigoSecreto(Jogo jogo, Participante participante) {
		Participante amigo = contexto.getRepositorioAmizades().buscarAmigo(jogo, participante);
		return amigo;
	}

	public List<Amizade> historicoAmizades(Jogo j) {
		return contexto.getRepositorioAmizades().buscarAmizadesDoJogo(j);
	}
}
