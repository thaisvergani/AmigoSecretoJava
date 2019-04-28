package com.identidade;

import java.util.List;

import com.entidades.*;

public interface DAOSugestoesPresentes extends DAO<SugestaoPresente, Long> {

	List<SugestaoPresente> buscarSugestoes(String nomeParticipante);
	
}
