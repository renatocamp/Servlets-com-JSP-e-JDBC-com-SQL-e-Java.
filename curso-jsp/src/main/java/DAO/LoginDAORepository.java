package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.SingleConnectionDB;
import Model.ModelLogin;

public class LoginDAORepository {
	
	private Connection connection;
	
	
	public LoginDAORepository() {
		connection = SingleConnectionDB.getConnection();
	}
	
	public boolean isAuthenticated(ModelLogin modelLogin) throws SQLException {
		
		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?) AND UPPER(senha) = UPPER(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return true; // AUTENTICADO
		}
		
		return false; // NÃO AUTENTICADO
		
	}

}
