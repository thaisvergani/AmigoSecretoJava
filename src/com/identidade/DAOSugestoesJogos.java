package com.identidade;

import java.util.List;

import com.entidades.SugestaoAmigoSecreto;

public interface DAOSugestoesJogos extends DAO<SugestaoAmigoSecreto, Long> {
	
	List<SugestaoAmigoSecreto> buscarTodasSugestoes();
	
}
