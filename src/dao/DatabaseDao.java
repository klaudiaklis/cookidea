package dao;

import model.Household;

public class DatabaseDao implements IDatabaseDao {

	public DatabaseDao() {
		
	}
	
	@Override
	public void saveUser(String user, String password) {
		System.out.println("User saved!");
	}

	@Override
	public Household getHouseholdByUser(String user) {
		return null;
	}

}
