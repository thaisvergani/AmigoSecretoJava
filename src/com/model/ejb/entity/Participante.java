package com.model.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Participante")
@SequenceGenerator(name = "Participante_Sequence", sequenceName = "participante_seq", 
allocationSize = 0, initialValue = 1)
public class Participante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Participante_Sequence")
	private Long id;

	@Column(name = "nome", length = 60, nullable = true)
	private String nome;
	
	@Column(name = "codinome", length = 60, nullable = true)
	private String codinome;
	
	@Column(name = "ramal")
	private int ramal;

	public Participante(String nome) {
		this.nome = nome;	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCodinome() {
		return codinome;
	}
	
	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}
	
	public int getRamal() {
		return ramal;
	}
	
	public void setRamal(int ramal) {
		this.ramal = ramal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participante other = (Participante) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
