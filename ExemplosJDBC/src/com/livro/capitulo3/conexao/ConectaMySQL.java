package com.livro.capitulo3.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {
	
	public static void main(String[] args) throws Exception {
		
		Connection conexao = null;
		
		try {
			
			String url = "jdbc:sqlite:C:\\java-web\\contato.db";
			//String usuario = "root";
			//String senha = "root";
			
			Class.forName("org.sqlite.JDBC");
			conexao = DriverManager.getConnection(url);
			
			System.out.println("conectado");
			
		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. ERRO: " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar a conexao. ERRO: " + e.getMessage());
			}
		}
	}
}
