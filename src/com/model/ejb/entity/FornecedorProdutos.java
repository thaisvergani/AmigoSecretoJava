package com.model.ejb.entity;

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
@Table(name="Fornecedor_Produtos")
@SequenceGenerator(name="Fornecedor_Produtos_Sequence", 
sequenceName="fornecedor_produtos_seq", allocationSize=0, initialValue=1)
public class FornecedorProdutos {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Fornecedor_Produtos_Sequence")
	private Long id;
	
	@Column(name="preco", nullable=true)
	private Double preco;
	
	@ManyToOne
	@JoinColumn(name="id_fornecedor")
	private Fornecedor fornecedor;
	
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;

	public FornecedorProdutos() {
	}

	public FornecedorProdutos(Double preco, Fornecedor fornecedor,
			Produto produto) {
		this.preco = preco;
		this.fornecedor = fornecedor;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		FornecedorProdutos other = (FornecedorProdutos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
