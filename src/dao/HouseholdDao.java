package dao;

import model.Household;

public class HouseholdDao implements IHouseholdDao {

	private IDatabaseDao databaseDao;
	
	public HouseholdDao() {
		databaseDao = new DatabaseDao();
	}
	
	@Override
	public boolean registerHousehold(String name, String password) {
		return databaseDao.saveHousehold(name,password);
	}

	@Override
	public Household getHouseholdByName(String name) {
		return databaseDao.getHouseholdByName(name);
	}
}