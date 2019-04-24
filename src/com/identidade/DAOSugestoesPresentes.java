package com.identidade;

import java.util.List;

import com.model.ejb.entity.*;

public interface DAOSugestoesPresentes extends DAO<SugestaoPresente, Long> {

	List<SugestaoPresente> buscarSugestoes(Participante participante);
	
}
