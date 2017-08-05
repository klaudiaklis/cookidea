package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BinaryProduct;
import model.CountableProduct;
import model.CousineType;
import model.DifficultyLevel;
import model.Household;
import model.LiquidProduct;
import model.MealType;
import model.Product;
import model.Recipe;
import model.UncountableProduct;

public class DatabaseDao implements IDatabaseDao {

	private static final String COOOKIDEA_CONNECTION_STRING = "jdbc:mysql://localhost/cookidea?user=root&password=root";

	public DatabaseDao() {
		// intentionally left empty
	}

	@Override
	public boolean saveHousehold(String name, String password) {
		System.out.println("User saved!");
		return true;
	}

	@Override
	public Household getHouseholdByName(String user) {
		Household household = null;
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM household WHERE login=?");
			prepareStatement.setString(1, user);
			ResultSet resultSet = prepareStatement.executeQuery();
			resultSet.next();
			household = new Household(resultSet.getInt("id"), user, resultSet.getString("password"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return household;
	}

	@Override
	public List<MealType> getAllMealTypes() {
		List<MealType> mealTypes = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM mealType");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				MealType mealType = new MealType(resultSet.getInt("id"), resultSet.getString("name"));
				mealTypes.add(mealType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mealTypes;
	}

	@Override
	public List<CousineType> getAllCousineTypes() {
		List<CousineType> cousineTypes = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM cousineType");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				CousineType cousineType = new CousineType(resultSet.getInt("id"), resultSet.getString("name"));
				cousineTypes.add(cousineType);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return cousineTypes;
	}

	@Override
	public List<DifficultyLevel> getAllDifficultyLevels() {
		List<DifficultyLevel> difficultyLevels = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM difficultyLevel");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				DifficultyLevel difficultyLevel = new DifficultyLevel(resultSet.getInt("id"),
						resultSet.getString("name"));
				difficultyLevels.add(difficultyLevel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return difficultyLevels;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM recipe");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Recipe recipe = new Recipe(resultSet.getInt("id"), //
						resultSet.getString("name"), //
						resultSet.getString("description"), //
						resultSet.getInt("duration"), //
						resultSet.getInt("mealTypeId"), //
						resultSet.getInt("cousineTypeId"), //
						resultSet.getInt("portions"), //
						resultSet.getInt("difficultyLevelId"));
				recipes.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipes;
	}

	@Override
	public List<Product> getProductsByRecipeId(int id) {
		List<Product> products = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			getLiquidProducts(id, products, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			getCountableProducts(id, products, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			getUncountableProducts(id, products, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			getBinaryProducts(id, products, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	private void getLiquidProducts(int id, List<Product> products, Connection conn) throws SQLException {
		String getProductsSql = "SELECT * FROM liquidProductRecipe, recipe, liquidProduct " + "WHERE recipe.id=? "
				+ "AND liquidProductRecipe.recipeId=? " + "AND liquidProduct.id=liquidProductRecipe.liquidProductId";
		ResultSet resultSet = getResultSet(id, conn, getProductsSql);
		while (resultSet.next()) {
			Product product = new LiquidProduct(resultSet.getInt("id"), resultSet.getString("liquidProduct.name"),
					resultSet.getDouble("volume"));
			products.add(product);
		}
	}

	private void getCountableProducts(int id, List<Product> products, Connection conn) throws SQLException {
		String getProductsSql = "SELECT * FROM countableProductRecipe, recipe, countableProduct " + "WHERE recipe.id=? "
				+ "AND countableProductRecipe.recipeId=? "
				+ "AND countableProduct.id=countableProductRecipe.countableProductId";
		ResultSet resultSet = getResultSet(id, conn, getProductsSql);
		while (resultSet.next()) {
			Product product = new CountableProduct(resultSet.getInt("id"), resultSet.getString("countableProduct.name"),
					resultSet.getInt("amount"));
			products.add(product);
		}
	}

	private void getUncountableProducts(int id, List<Product> products, Connection conn) throws SQLException {
		String getProductsSql = "SELECT * FROM uncountableProductRecipe, recipe, uncountableProduct "
				+ "WHERE recipe.id=? " + "AND uncountableProductRecipe.recipeId=? "
				+ "AND uncountableProduct.id=uncountableProductRecipe.uncountableProductId";
		ResultSet resultSet = getResultSet(id, conn, getProductsSql);
		while (resultSet.next()) {
			Product product = new UncountableProduct(resultSet.getInt("id"),
					resultSet.getString("uncountableProduct.name"), resultSet.getDouble("weight"));
			products.add(product);
		}
	}

	private void getBinaryProducts(int id, List<Product> products, Connection conn) throws SQLException {
		String getProductsSql = "SELECT * FROM binaryProductRecipe, recipe, binaryProduct " + "WHERE recipe.id=? "
				+ "AND binaryProductRecipe.recipeId=? " + "AND binaryProduct.id=binaryProductRecipe.binaryProductId";
		ResultSet resultSet = getResultSet(id, conn, getProductsSql);
		while (resultSet.next()) {
			Product product = new BinaryProduct(resultSet.getInt("id"), resultSet.getString("binaryProduct.name"),
					true);
			products.add(product);
		}
	}

	private ResultSet getResultSet(int id, Connection conn, String getProductsSql) throws SQLException {
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		prepareStatement.setInt(1, id);
		prepareStatement.setInt(2, id);
		ResultSet resultSet = prepareStatement.executeQuery();
		return resultSet;
	}
}