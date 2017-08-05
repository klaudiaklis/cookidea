package dao;

import java.util.List;

import model.MealType;

public class MealTypeDao implements IMealTypeDao {

private IDatabaseDao databaseDao;
	
	public MealTypeDao() {
		databaseDao = new DatabaseDao();
	}
		
	@Override
	public List<MealType> getAllMealTypes() {
		return databaseDao.getAllMealTypes();
	}

}
