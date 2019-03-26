package com.testes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {

	public static void main(String[] args) {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado!");
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JPAHibernate", "postgres", "postgres");

			if (conn != null) {
				System.out.println("Conexão aberta");
				System.out.println(conn);
				System.out.println("Produtos");
				System.out.println("Código\t\t\tNome\t\t\tDescrição");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from produto");
				while (rs.next()) {
					System.out.print(rs.getInt("id") + "\t\t\t");
					System.out.print(rs.getString("nome") + "\t\t\t");
					System.out.println(rs.getString("descricao"));
				}

				System.out.println("Fechando...");
				rs.close();
				stmt.close();
				conn.close();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
