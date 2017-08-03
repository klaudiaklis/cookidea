package dao;

import model.Household;

public class UserDao implements IUserDao {

	private IDatabaseDao databaseDao;
	
	public UserDao() {
		databaseDao = new DatabaseDao();
	}
	
	@Override
	public void registerUser(String user, String password) {
		databaseDao.saveUser(user,password);
	}

	@Override
	public Household getHouseholdByUser(String user) {
		return databaseDao.getHouseholdByUser(user);
	}

}
