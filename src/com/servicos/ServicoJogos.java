package com.servicos;

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
import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;

public class ServicoJogos extends ServicoBase {

	public ServicoJogos(ContextoAmigoSecreto contexto) {
		super(contexto);
	}
	
	public Jogo criarNovo(Date fim) throws ExcecaoValidacaoServico {
		return criarNovo(fim, null);
	}
	
	public Jogo criarNovo(Date fim, String nome) throws ExcecaoValidacaoServico {
		List<Participante> participantes = contexto.getRepositorioParticipantes().buscarTodosParticipantes();
		if (participantes.size() < 3) {
			throw new ExcecaoValidacaoServico("Necessário possuir pelo menos 3 participantes para criar um amigo secreto");
		}
		
		if (nome == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			nome = "Amigo Secreto dia " +  formatter.format(fim);			
		}
		
		List<Jogo> ultimosJogos = contexto.getRepositorioJogos().buscarUltimosJogos(4);
		
		Jogo jogo = new Jogo();		
		jogo.setNome(nome);
		jogo.setInicio(new Date());
		jogo.setFim(fim);
		
		contexto.getRepositorioJogos().adicionar(jogo);
		
		List<Amizade> amizadesJogo = sorteio(jogo, participantes, ultimosJogos);
		
		contexto.getRepositorioJogos().persistirAmizadesDoJogo(amizadesJogo);
		
		return jogo;
	}
	
	private List<Amizade> sorteio(Jogo jogo, List<Participante> participantes, List<Jogo> ultimosJogos) {
		
		// Se tivermos mais jogos do que participantes não tem como evitar a repetição de amizades 
		while (participantes.size() < ultimosJogos.size() - 1) {
			ultimosJogos.remove(ultimosJogos.size() - 1);
		}
		
		Collections.shuffle(participantes);
		
		List<Amizade> amizades = new ArrayList<Amizade>(participantes.size());
		
		for (Participante p : participantes) {			
			amizades.add(new Amizade(p, jogo));
		}
		
		Map<Long, List<Long>> amizadesDosUltimosJogos = new HashMap<Long, List<Long>>();
		
		for (Jogo j : ultimosJogos) {
			for (Amizade a : contexto.getRepositorioJogos().buscarAmizadesDoJogo(j)) {
				Long idParticipante = a.getParticipante().getId();
				List<Long> ultimasAmizadesParticipante = amizadesDosUltimosJogos.get(idParticipante);
				if (ultimasAmizadesParticipante == null) {
					ultimasAmizadesParticipante = new ArrayList<Long>();
					amizadesDosUltimosJogos.put(idParticipante, ultimasAmizadesParticipante);
				}
				
				ultimasAmizadesParticipante.add(a.getAmigoSecreto().getId());
			}
		}
		
		Random random = new Random();
		
		for (Amizade amizade : amizades) {
			List<Long> participantesProibidos = amizadesDosUltimosJogos.get(amizade.getParticipante().getId());
			Participante amigoSecreto = null;
			
			do {
				int indice = random.nextInt(participantes.size());
				amigoSecreto = participantes.get(indice);
			} while (participantesProibidos != null && !participantesProibidos.contains(amigoSecreto.getId()));
			
			amizade.setAmigoSecreto(amigoSecreto);
			participantes.remove(amigoSecreto);	
		}
		
		return amizades;
	}
}
