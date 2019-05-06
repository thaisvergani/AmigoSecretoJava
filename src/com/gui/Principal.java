package com.gui;

import com.dao.ContextoDAOAmigoSecreto;
import com.identidade.ContextoAmigoSecreto;

public class Principal {

	public static void main(String[] args) {
		ContextoAmigoSecreto contexto = new ContextoDAOAmigoSecreto();		
		Escritor.EscreverLinha("Informe o login");
		String nome_user = Leitor.ler();
		if (nome_user.equals("admin")) {
			new MenuAdmin(contexto).entrar();		

		}else {
			new MenuParticipante(contexto, nome_user).entrar();		

		}

	}
	
	
}
