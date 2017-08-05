package dao;

import java.util.List;

import model.Recipe;

public class RecipeDao implements IRecipeDao {

private IDatabaseDao databaseDao;
	
	public RecipeDao() {
		databaseDao = new DatabaseDao();
	}
	
	@Override
	public List<Recipe> getAllRecipes() {
		return databaseDao.getAllRecipes();
	}

}
