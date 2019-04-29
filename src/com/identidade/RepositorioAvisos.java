package com.identidade;

import java.util.List;

import com.entidades.Aviso;

public interface RepositorioAvisos extends Repositorio<Aviso, Long> {

	List<Aviso> buscarTodosAvisos();
	
}
