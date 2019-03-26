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
@Table(name = "Cliente")
@SequenceGenerator(name = "Cliente_Sequence", sequenceName = "cliente_seq", 
allocationSize = 0, initialValue = 1)
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cliente_Sequence")
	private Long id;

	@Column(name = "nome", length = 60, nullable = true)
	private String nome;

	@Column(name = "endereco", length = 120, nullable = true)
	private String endereco;

	@Column(name = "cartaoCredito", length = 16, nullable = true)
	private String cartaoCredito;

	public Cliente() {
	}

	public Cliente(String nome, String endereco, String cartaoCredito) {
		this.nome = nome;
		this.endereco = endereco;
		this.cartaoCredito = cartaoCredito;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(String cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
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
		Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", endereco="
				+ endereco + ", cartaoCredito=" + cartaoCredito + "]";
	}

}
