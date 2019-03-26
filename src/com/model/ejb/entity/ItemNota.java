package com.model.ejb.entity;

import java.io.Serializable;

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
@Table(name="item_nota")
@SequenceGenerator(name="Item_Nota_Sequence", 
sequenceName="item_nota_seq", allocationSize=0, initialValue=1)
public class ItemNota implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Item_Nota_Sequence")
	private Long id;

	@Column(name="valor", nullable=false)
	private Double valor;
	
	@Column(name="quantidade", nullable=false)
	private Integer quantidade;

	@ManyToOne
	@JoinColumn(name="id_fornecedor_produto")
	private FornecedorProdutos fornecedorProdutos;

	public ItemNota() {
	}

	public ItemNota(Double valor, Integer quantidade,
			FornecedorProdutos fornecedorProdutos) {
		super();
		this.valor = valor;
		this.quantidade = quantidade;
		this.fornecedorProdutos = fornecedorProdutos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public FornecedorProdutos getFornecedorProdutos() {
		return fornecedorProdutos;
	}

	public void setFornecedorProdutos(FornecedorProdutos fornecedorProdutos) {
		this.fornecedorProdutos = fornecedorProdutos;
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
		ItemNota other = (ItemNota) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
