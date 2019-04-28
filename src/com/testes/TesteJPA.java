package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.entidades.Amizade;
import com.entidades.Participante;
import com.entidades.SugestaoAmigoSecreto;
import com.entidades.SugestaoPresente;

public class TesteJPA {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AmigoSecreto");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Exclui todas as notas fiscais
//
//		Query q = em.createQuery("delete from ItemNota i");
//		q.executeUpdate();
//
//		q = em.createQuery("delete from NotaFiscal nf");
//		q.executeUpdate();
//
//		// Exclui todos os clientes
//		q = em.createQuery("delete from Cliente c");
//		q.executeUpdate();
//
//		// Exclui todos os vínculos fornecedor x produto
//		q = em.createQuery("delete from FornecedorProdutos fp");
//		q.executeUpdate();
//
//		// Exclui todos os fornecedores
//		q = em.createQuery("delete from Fornecedor f");
//		q.executeUpdate();
//
//		// Exclui todos os produtos
//		q = em.createQuery("delete from Produto p");
//		q.executeUpdate();
//
//		em.flush();

		Participante c = new Participante("Jose");
		em.persist(c);
		Long idJose = c.getId();
//
//		Amizade f = new Amizade("Petrobrás",
//				"Empresa brasileira de petróleo e derivados");
//		em.persist(f);
//		Long idPetrobras = f.getId();
//
//		f = new Amizade("Shell", "Multinacional americana de petróleo");
//		em.persist(f);
//		Long idShell = f.getId();
//
//		// Cadastra produtos
//		SugestaoPresente p = new SugestaoPresente("Gasolina", "Galão de 50 litros");
//		em.persist(p);
//		Long idGasolina = p.getId();
//
//		p = new SugestaoPresente("Diesel", "Galão de 50 litros");
//		em.persist(p);
//		Long idDiesel = p.getId();
//
//		p = new SugestaoPresente("Óleo de motor", "Embalagem 1 litro");
//		em.persist(p);
//		Long idOleo = p.getId();
//
//		// Vincula fornecedores com produtos
//		Amizade f1 = em.find(Amizade.class, idShell);
//		SugestaoPresente p1 = em.find(SugestaoPresente.class, idDiesel);
//		SugestaoAmigoSecreto fp = new SugestaoAmigoSecreto(200d, f1, p1);
//		em.persist(fp);
//
//		Amizade f2 = em.find(Amizade.class, idPetrobras);
//		SugestaoPresente p2 = em.find(SugestaoPresente.class, idGasolina);
//		SugestaoAmigoSecreto fp2 = new SugestaoAmigoSecreto(150d, f2, p2);
//		em.persist(fp2);
//
//		SugestaoPresente p3 = em.find(SugestaoPresente.class, idOleo);
//		SugestaoAmigoSecreto fp3 = new SugestaoAmigoSecreto(13d, f2, p3);
//		em.persist(fp3);
//
//		System.out.println("Cliente José ");
//		System.out.println("---------------------------------------");
//
//		// busca um cliente e mostra
//		Participante c2 = em.find(Participante.class, idJose);
//		System.out.println(c2);
//
//        // fazer aqui a execução das named queries


		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
