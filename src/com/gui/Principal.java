package com.gui;

import com.dao.ContextoDAOAmigoSecreto;
import com.identidade.ContextoAmigoSecreto;

public class Principal {

	public static void main(String[] args) {
		ContextoAmigoSecreto contexto = new ContextoDAOAmigoSecreto();		
		// login
		new MenuParticipante(contexto).entrar();		
	}
	
	
}
