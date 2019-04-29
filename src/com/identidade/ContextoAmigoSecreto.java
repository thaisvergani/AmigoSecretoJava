package com.identidade;

public interface ContextoAmigoSecreto {

	RepositorioAvisos getRepositorioAvisos();
	
	RepositorioJogos getRepositorioJogos();
	
	RepositorioMensagens getRepositorioMensagens();
	
	RepositorioParticipantes getRepositorioParticipantes();
	
	RepositorioSugestoesJogos getRepositorioSugestoesJogos();
	
	RepositorioSugestoesPresentes getRepositorioSugestoesPresentes();
}
