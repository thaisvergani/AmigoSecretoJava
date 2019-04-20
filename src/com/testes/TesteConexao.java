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
			System.out.println("Driver nao encontrado!");
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/amigo_secreto", "postgres", "postgres");

			if (conn != null) {
				System.out.println("Conexao aberta: " + conn);
				System.out.println("Jogos");
				System.out.println("Codigo\t\t\tNome\t\t\tInicio\t\t\tFim");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Jogo");
				while (rs.next()) {
					System.out.print(rs.getInt("id") + "\t\t\t");
					System.out.print(rs.getString("nome") + "\t\t\t");
					System.out.println(rs.getDate("inicio") + "\t\t\t");
					System.out.println(rs.getDate("fim") + "\t\t\t");
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
