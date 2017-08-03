package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Household;

public class DatabaseDao implements IDatabaseDao {

	private static final String COOOKIDEA_CONNECTION_STRING = "jdbc:mysql://localhost/cookidea?user=root&password=root";
	public DatabaseDao() {
		// TODO: implement me! ... or remove...
	}
	
	@Override
	public void saveUser(String user, String password) {
		System.out.println("User saved!");
	}

	@Override
	public Household getHouseholdByUser(String user) {
		Household household = null;
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)){
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM household WHERE login=?");
			prepareStatement.setString(1, user);
			ResultSet executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				household = new Household(executeQuery.getInt("id"), user, executeQuery.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return household;
	}

}
