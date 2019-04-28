package com.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SugestaoPresente")
@SequenceGenerator(name="SugestaoPresente_Sequence", 
sequenceName="sugestao_presente_seq", allocationSize=0, initialValue=1)
public class SugestaoPresente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SugestaoPresente_Sequence")
	private Long id;
		
	@ManyToOne
	@JoinColumn(name="jogo")
	private Jogo jogo;

	@ManyToOne
	@JoinColumn(name="participante")
	private Participante participante;
	
	@Column(name="descricao", length=255)
	private String descricao;	

	public SugestaoPresente(Jogo jogo, Participante participante, String descricao) {
		this.jogo = jogo;
		this.participante = participante;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		SugestaoPresente other = (SugestaoPresente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
