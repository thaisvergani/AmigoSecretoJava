package com.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Amizade")
@SequenceGenerator(name="Amizade_Sequence", sequenceName="amizade_seq", allocationSize=0, initialValue=1)
public class Amizade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Amizade_Sequence")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="jogo")
	private Jogo jogo;
	
	@ManyToOne
	@JoinColumn(name="participante")
	private Participante participante;
	
	@ManyToOne
	@JoinColumn(name="amigo_secreto")
	private Participante amigoSecreto;
	
	public Amizade(Participante participante, Jogo jogo) {
		this.participante = participante;
		this.jogo = jogo;
	}
	
	public Long getId() {
		return id;
	}
	
	public Jogo getJogo() {
		return jogo;
	}
	
	public Participante getParticipante() {
		return participante;
	}
	
	public Participante getAmigoSecreto() {
		return amigoSecreto;
	}
	
	public void setAmigoSecreto(Participante amigoSecreto) {
		this.amigoSecreto = amigoSecreto;
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
		Amizade other = (Amizade) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
