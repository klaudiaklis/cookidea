package dao;

import java.util.List;

import model.CousineType;

public class CousineTypeDao implements ICousineTypeDao{
	
private IDatabaseDao databaseDao;
	
	public CousineTypeDao() {
		databaseDao = new DatabaseDao();
	}
		
	@Override
	public List<CousineType> getAllCousineTypes() {
		return databaseDao.getAllCousineTypes();
	}
}
