package com.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Moderador")
@SequenceGenerator(name="Moderador_Sequence", sequenceName="Moderador_seq", allocationSize=0, initialValue=1)
public class Moderador {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Moderador_Sequence")
	private Long id;
	
	@Column(name="jogo", length=60, nullable=true )
	private Jogo jogo;

	@ManyToOne
	@JoinColumn(name="participante")
	private Participante participante;
	
	public Moderador(Jogo jogo, Participante participante) {
		this.jogo = jogo;
		this.participante = participante;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Moderador other = (Moderador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
