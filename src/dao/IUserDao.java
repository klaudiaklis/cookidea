package dao;

import model.Household;

public interface IUserDao {

	void registerUser(String user, String password);
	
	Household getHouseholdByUser(String user);

}
