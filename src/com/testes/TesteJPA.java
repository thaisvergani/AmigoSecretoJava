package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.model.ejb.entity.Cliente;
import com.model.ejb.entity.Fornecedor;
import com.model.ejb.entity.FornecedorProdutos;
import com.model.ejb.entity.Produto;

public class TesteJPA {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPAStandalone");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Exclui todas as notas fiscais

		Query q = em.createQuery("delete from ItemNota i");
		q.executeUpdate();

		q = em.createQuery("delete from NotaFiscal nf");
		q.executeUpdate();

		// Exclui todos os clientes
		q = em.createQuery("delete from Cliente c");
		q.executeUpdate();

		// Exclui todos os vínculos fornecedor x produto
		q = em.createQuery("delete from FornecedorProdutos fp");
		q.executeUpdate();

		// Exclui todos os fornecedores
		q = em.createQuery("delete from Fornecedor f");
		q.executeUpdate();

		// Exclui todos os produtos
		q = em.createQuery("delete from Produto p");
		q.executeUpdate();

		em.flush();

		// Cadastra e busca clientes
		Cliente c = new Cliente("José", "Caxias do Sul", "1234432155557777");
		em.persist(c);
		Long idJose = c.getId();

		// Cadastra fornecedores
		Fornecedor f = new Fornecedor("Petrobrás",
				"Empresa brasileira de petróleo e derivados");
		em.persist(f);
		Long idPetrobras = f.getId();

		f = new Fornecedor("Shell", "Multinacional americana de petróleo");
		em.persist(f);
		Long idShell = f.getId();

		// Cadastra produtos
		Produto p = new Produto("Gasolina", "Galão de 50 litros");
		em.persist(p);
		Long idGasolina = p.getId();

		p = new Produto("Diesel", "Galão de 50 litros");
		em.persist(p);
		Long idDiesel = p.getId();

		p = new Produto("Óleo de motor", "Embalagem 1 litro");
		em.persist(p);
		Long idOleo = p.getId();

		// Vincula fornecedores com produtos
		Fornecedor f1 = em.find(Fornecedor.class, idShell);
		Produto p1 = em.find(Produto.class, idDiesel);
		FornecedorProdutos fp = new FornecedorProdutos(200d, f1, p1);
		em.persist(fp);

		Fornecedor f2 = em.find(Fornecedor.class, idPetrobras);
		Produto p2 = em.find(Produto.class, idGasolina);
		FornecedorProdutos fp2 = new FornecedorProdutos(150d, f2, p2);
		em.persist(fp2);

		Produto p3 = em.find(Produto.class, idOleo);
		FornecedorProdutos fp3 = new FornecedorProdutos(13d, f2, p3);
		em.persist(fp3);

		System.out.println("Cliente José ");
		System.out.println("---------------------------------------");

		// busca um cliente e mostra
		Cliente c2 = em.find(Cliente.class, idJose);
		System.out.println(c2);

        // fazer aqui a execução das named queries


		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
