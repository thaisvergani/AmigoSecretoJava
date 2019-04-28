package com.identidade;

import java.util.List;

import com.entidades.SugestaoAmigoSecreto;

public interface RepositorioSugestoesJogos extends Repositorio<SugestaoAmigoSecreto, Long> {
	
	List<SugestaoAmigoSecreto> buscarTodasSugestoes();
	
}
