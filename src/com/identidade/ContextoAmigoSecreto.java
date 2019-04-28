package com.identidade;

public interface ContextoAmigoSecreto {

	RepositorioJogos getRepositorioJogos();
	
	RepositorioMensagens getRepositorioMensagens();
	
	RepositorioParticipantes getRepositorioParticipantes();
	
	RepositorioSugestoesJogos getRepositorioSugestoesJogos();
	
	RepositorioSugestoesPresentes getRepositorioSugestoesPresentes();
}
