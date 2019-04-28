package com.entidades;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="Jogo")
@SequenceGenerator(name="Jogo_Sequence", 
sequenceName="jogo_seq", allocationSize=0, initialValue=1)
public class Jogo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Jogo_Sequence")
	private Long id;
	
	@Column(name="inicio", nullable=false)
	private Date inicio;
	
	@Column(name="fim", nullable=false)
	private Date fim;
		
	@Column(name="nome", nullable=false)
	private String nome;


	public Jogo(String nome, Date inicio) {
		this.nome = nome;
		this.inicio = inicio;
	}
	
	public Long getId() {
		return id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogo other = (Jogo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
