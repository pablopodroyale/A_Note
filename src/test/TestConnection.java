package test;

import java.sql.*;

public class TestConnection

{
	private static Connection cn;

	public static Connection getConection() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection("jdbc:sqlserver://localhost;integratedSecurity=true;");
		} catch (ClassNotFoundException e) {
			cn = null;
		}
		return cn;
	}

	public static void main(String[] args) throws SQLException {
		Connection pruebaCn = TestConnection.getConection();
		if (pruebaCn != null) {
			System.out.println("conectado");
		}else {
			System.out.println("desconectado");
		}

	
	}
}
