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
@Table(name="SugestaoAmigoSecreto")
@SequenceGenerator(name="SugestaoAmigoSecreto_Sequence", sequenceName="fornecedor_produtos_seq", allocationSize=0, initialValue=1)
public class SugestaoAmigoSecreto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SugestaoAmigoSecreto_Sequence")
	private Long id;
		
	@ManyToOne
	@JoinColumn(name="participante")
	private Participante participante;
	
	@Column(name="descricao", length=255)
	private String descricao;	

	public SugestaoAmigoSecreto(Participante participante, String descricao) {
		this.participante = participante;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Participante getParticipante() {
		return participante;
	}
	
	public String getDescricao() {
		return descricao;
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
		SugestaoAmigoSecreto other = (SugestaoAmigoSecreto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
