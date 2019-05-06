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
@Table(name="mensagem")
@SequenceGenerator(name="Mensagem_Sequence", sequenceName="mensagem_seq", allocationSize=0, initialValue=1)
public class Mensagem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Mensagem_Sequence")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="jogo", nullable=true )
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
	
	public Mensagem(String texto, Date data, Participante remetente, Participante destinatario, Jogo jogo) {
		this.texto = texto;
		this.data = data;
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.jogo = jogo;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Participante getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Participante destinatario) {
		this.destinatario = destinatario;
	}

	public Participante getRemetente() {
		return remetente;
	}

	public void setRemetente(Participante remetente) {
		this.remetente = remetente;
	}

}
