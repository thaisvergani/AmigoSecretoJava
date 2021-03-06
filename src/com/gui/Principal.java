package com.gui;

import com.dao.ContextoDAOAmigoSecreto;
import com.entidades.Participante;
import com.identidade.ContextoAmigoSecreto;

public class Principal {

	public static void main(String[] args) {
		
		ContextoAmigoSecreto contexto = null;
		try {
			contexto = new ContextoDAOAmigoSecreto();		
			Console.escrever("Informe o login: ");
			String nome_user = Console.ler();			
			if (nome_user.equals("admin")) {
				new MenuAdmin(contexto).entrar();		
			} else {
				MenuParticipante menu_participante = new MenuParticipante(contexto, nome_user);	
				Participante participanteAtual = menu_participante.getParticipanteAtual();
				if (participanteAtual == null) {
					Console.escreverLinha("Participante nao encontrado");
				}else {
					menu_participante.entrar();
				}
			}
		}
		catch (Exception e) {
			Console.escreverLinha("Falha na execucao");
			e.printStackTrace();
		}
		finally {
			if (contexto != null) {
				contexto.close();
			}
		}
	}
}