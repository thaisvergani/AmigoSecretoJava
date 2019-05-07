package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.identidade.ContextoAmigoSecreto;
import com.identidade.RepositorioAvisos;
import com.identidade.RepositorioJogos;
import com.identidade.RepositorioMensagens;
import com.identidade.RepositorioParticipantes;
import com.identidade.RepositorioSugestoesJogos;
import com.identidade.RepositorioSugestoesPresentes;
import com.identidade.RepositorioAmizades;

public class ContextoDAOAmigoSecreto implements ContextoAmigoSecreto {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	private RepositorioAvisos repositorioAvisos;
	private RepositorioJogos repositorioJogos;
	private RepositorioMensagens repositorioMensagens;
	private RepositorioParticipantes repositorioParticipantes;
	private RepositorioSugestoesJogos repositorioSugestoesJogos;
	private RepositorioSugestoesPresentes repositorioSugestoesPresentes;
	private RepositorioAmizades repositorioAmizades;
	
	public ContextoDAOAmigoSecreto() {
		emf = Persistence.createEntityManagerFactory("AmigoSecreto");
		em = emf.createEntityManager();
		
		this.repositorioAvisos = new RepositorioDAOAvisos(em);
		this.repositorioJogos = new RepositorioDAOJogos(em);
		this.repositorioMensagens = new RepositorioDAOMensagens(em);
		this.repositorioParticipantes = new RepositorioDAOParticipantes(em);
		this.repositorioSugestoesJogos = new RepositorioDAOSugestoesJogos(em);
		this.repositorioSugestoesPresentes = new RepositorioDAOSugestoesPresentes(em);
		this.repositorioAmizades = new RepositorioDAOAmizades(em);
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
	
	@Override
	public RepositorioAmizades getRepositorioAmizades() {
		return repositorioAmizades;
	}

	@Override
	public void close() {
		em.close();
		emf.close();
	}
	
	
}
