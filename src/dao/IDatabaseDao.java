package dao;

import model.Household;

public interface IDatabaseDao {

	void saveUser(String user, String password);

	Household getHouseholdByUser(String user);

}
