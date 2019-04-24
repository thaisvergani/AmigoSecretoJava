package com.identidade;

import com.model.ejb.entity.Participante;

public interface DAOParticipantes extends DAO<Participante, Long> {

	Participante buscarParticipante(String nomeOuCodinome);
}
