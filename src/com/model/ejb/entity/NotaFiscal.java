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
@Table(name="nota_fiscal")
@SequenceGenerator(name="NotaFiscal_Sequence", 
sequenceName="nota_fiscal_seq", allocationSize=0, initialValue=1)
public class NotaFiscal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NotaFiscal_Sequence")
	private Long id;

	@Column(name="nro_nota", nullable=false)
	private Integer nro;
	
	@Column(name="data", nullable=false)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_nota")
	private List<ItemNota> itens;
	
	public NotaFiscal() {
		this.itens = new ArrayList<ItemNota>();
	}

	public NotaFiscal(Integer nro, Date data, Cliente cliente) {
		this();
		this.nro = nro;
		this.data = data;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemNota> getItens() {
		return itens;
	}

	public void setItens(List<ItemNota> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemNota item) {
		this.itens.add(item);
	}
	
	public void removeItem(ItemNota item) {
		this.itens.add(item);
	}
	
	public Double getTotal() {
		Double total = 0d;
		for (ItemNota item :this.itens) {
			total += item.getValor() * item.getQuantidade();
		}
		return total;
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
		NotaFiscal other = (NotaFiscal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("NotaFiscal [id=" + id + ", nro=" + nro + ", data=" + data
				+ ",\n cliente=" + cliente + "]\n");
		sb.append("------------------------------------------\n");
		for (int i = 0; i < this.itens.size(); i++) {
			ItemNota item =  this.itens.get(i);
			sb.append("(" + (i + 1) + "):" + item.getQuantidade() + " ");
			sb.append(item.getValor() + " ");
			sb.append(item.getFornecedorProdutos().getProduto().getNome() + "\n");
		}
		sb.append("------------------------------------------\n");
		sb.append("Total da nota : " + this.getTotal());
		return sb.toString();
		
	}
	
}
