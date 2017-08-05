package dao;

import model.Household;

public interface IHouseholdDao {

	boolean registerHousehold(String name, String password);
	
	Household getHouseholdByName(String name);

}
