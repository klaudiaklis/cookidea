package dao;

import java.util.List;

import model.Recipe;

public interface IRecipeDao {
	List<Recipe> getAllRecipes();
}
