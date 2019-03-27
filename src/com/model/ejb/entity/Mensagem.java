package com.model.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="mensagem")
@SequenceGenerator(name="Mensagem_Sequence", 
sequenceName="mensagem_seq", allocationSize=0, initialValue=1)
public class Mensagem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Mensagem_Sequence")
	private Long id;
	
	@Column(name="jogo", length=60, nullable=true )
	private Jogo jogo;
	
	@Column(name="texto", nullable=false)
	private String texto;
	
	@Column(name="data", nullable=false)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="destinatario")
	private Participante destinatario;
	
	@ManyToOne
	@JoinColumn(name="remetente")
	private Participante remetente;
	
	public Mensagem(
			String texto,
			Date data, 
			Participante remetente,
			Participante destinatario) {
		this.texto = texto;
		this.data = data;
		this.remetente = remetente;
		this.destinatario = destinatario;
	}

	
}
