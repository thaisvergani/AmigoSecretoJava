package com.model.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Fornecedor")
@SequenceGenerator(name="Fornecedor_Sequence", 
sequenceName="fornecedor_seq", allocationSize=0, initialValue=1)
public class Fornecedor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Fornecedor_Sequence")
	private Long id;
	
	@Column(name="nome", length=60, nullable=true )
	private String nome;
	
	@Column(name="descricao", length=255, nullable=true )
	private String descricao;
	
	@OneToMany(mappedBy="fornecedor")
	private List<FornecedorProdutos> fornecedorProdutos;
	
	public Fornecedor() {
		this.fornecedorProdutos = new ArrayList<FornecedorProdutos>();
	}

	public Fornecedor(String nome, String descricao) {
		this();
		this.nome = nome;
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<FornecedorProdutos> getFornecedorProdutos() {
		return fornecedorProdutos;
	}

	public void setFornecedorProdutos(List<FornecedorProdutos> fornecedorProdutos) {
		this.fornecedorProdutos = fornecedorProdutos;
	}
	
	public void addFornecedorProdutos(FornecedorProdutos fornecedorProdutos) {
		this.fornecedorProdutos.add(fornecedorProdutos);
	}
	
	public void removeFornecedorProdutos(FornecedorProdutos fornecedorProdutos) {
		this.fornecedorProdutos.remove(fornecedorProdutos);
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
		Fornecedor other = (Fornecedor) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
