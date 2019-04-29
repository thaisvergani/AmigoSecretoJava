package com.dao;

import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioAvisos;
import com.identidade.RepositorioJogos;
import com.identidade.RepositorioMensagens;
import com.identidade.RepositorioParticipantes;
import com.identidade.RepositorioSugestoesJogos;
import com.identidade.RepositorioSugestoesPresentes;

public class ContextoDAOAmigoSecreto implements ContextoAmigoSecreto {

	private RepositorioAvisos repositorioAvisos;
	private RepositorioJogos repositorioJogos;
	private RepositorioMensagens repositorioMensagens;
	private RepositorioParticipantes repositorioParticipantes;
	private RepositorioSugestoesJogos repositorioSugestoesJogos;
	private RepositorioSugestoesPresentes repositorioSugestoesPresentes;
	
	public ContextoDAOAmigoSecreto() {
		this.repositorioAvisos = new RepositorioDAOAvisos();
		this.repositorioJogos = new RepositorioDAOJogos();
		this.repositorioMensagens = new RepositorioDAOMensagens();
		this.repositorioParticipantes = new RepositorioDAOParticipantes();
		this.repositorioSugestoesJogos = new RepositorioDAOSugestoesJogos();
		this.repositorioSugestoesPresentes = new RepositorioDAOSugestoesPresentes();
	}
	
	@Override
	public RepositorioAvisos getRepositorioAvisos() {
		return repositorioAvisos;
	}
	
	@Override
	public RepositorioJogos getRepositorioJogos() {
		return repositorioJogos;
	}

	@Override
	public RepositorioMensagens getRepositorioMensagens() {
		return repositorioMensagens;
	}

	@Override
	public RepositorioParticipantes getRepositorioParticipantes() {
		return repositorioParticipantes;
	}

	@Override
	public RepositorioSugestoesJogos getRepositorioSugestoesJogos() {
		return repositorioSugestoesJogos;
	}

	@Override
	public RepositorioSugestoesPresentes getRepositorioSugestoesPresentes() {
		return repositorioSugestoesPresentes;
	}
}
