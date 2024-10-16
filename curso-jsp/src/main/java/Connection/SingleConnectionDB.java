package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnectionDB {
	
	private static String URL_JDBC = "jdbc:postgresql://localhost:5432/curso_jsp?autoReconnect=true";
	private static String USUARIO = "postgres";
	private static String PASSWORD = "root";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionDB() {
		conectar();
	}
	
	public static void conectar() {
		
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver"); // Carrega o Driver do Banco de Dados PostgreSQL
				connection = DriverManager.getConnection(URL_JDBC, USUARIO, PASSWORD);	
				connection.setAutoCommit(true);
			}

		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}

}
