package dao;

import java.util.List;

import model.DifficultyLevel;

public class DifficultyLevelDao implements IDifficultyLevelDao {

private IDatabaseDao databaseDao;
	
	public DifficultyLevelDao() {
		databaseDao = new DatabaseDao();
	}   // konstruktor
	
	@Override
	public List<DifficultyLevel> getAllDifficultyLevels() {
		return databaseDao.getAllDifficultyLevels();
	}

}
