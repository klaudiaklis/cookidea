package dao;

import java.util.List;

import model.CousineType;
import model.DifficultyLevel;
import model.Household;
import model.MealType;
import model.Product;
import model.Recipe;

public interface IDatabaseDao {

	boolean saveHousehold(String name, String password);

	Household getHouseholdByName(String name);

	List<MealType> getAllMealTypes();

	List<CousineType> getAllCousineTypes();

	List<DifficultyLevel> getAllDifficultyLevels();

	List<Recipe> getAllRecipes();

	List<Product> getProductsByRecipeId(int id);

	List<Product> getAllProducts();

	boolean addProductForHousehold(Product addedProduct, int householdId, String tableName);

	List<Product> getProductsByHouseholdId(int id);

	boolean remove(Product selectedProduct, int householdId);

}
