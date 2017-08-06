package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.BinaryProduct;
import model.CountableProduct;
import model.CousineType;
import model.DifficultyLevel;
import model.Household;
import model.LiquidProduct;
import model.MealType;
import model.Product;
import model.ProductCategoryEnum;
import model.Recipe;
import model.UncountableProduct;

public class DatabaseDao implements IDatabaseDao {

	private static final String COOOKIDEA_CONNECTION_STRING = "jdbc:mysql://localhost/cookidea?user=root&password=root";

	public DatabaseDao() {
		// intentionally left empty
	}

	@Override
	public boolean saveHousehold(String name, String password) {
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("INSERT INTO household VALUES(default, ?, ?)");
			prepareStatement.setString(1, name);
			prepareStatement.setString(2, password);
			int executeUpdate = prepareStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Household getHouseholdByName(String user) {
		Household household = null;
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM household WHERE login=?");
			prepareStatement.setString(1, user);
			ResultSet resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				household = new Household(resultSet.getInt("id"), user, resultSet.getString("password"));
			}

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
						resultSet.getInt("difficultyLevelId"), new ArrayList<>());
				recipes.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Recipe recipe : recipes) {
			recipe.setProducts(getProductsByRecipeId(recipe.getId()));
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
					resultSet.getDouble("volume"), null);
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
					resultSet.getInt("amount"), null);
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
					resultSet.getString("uncountableProduct.name"), resultSet.getDouble("weight"), null);
			products.add(product);
		}
	}

	private void getBinaryProducts(int id, List<Product> products, Connection conn) throws SQLException {
		String getProductsSql = "SELECT * FROM binaryProductRecipe, recipe, binaryProduct " + "WHERE recipe.id=? "
				+ "AND binaryProductRecipe.recipeId=? " + "AND binaryProduct.id=binaryProductRecipe.binaryProductId";
		ResultSet resultSet = getResultSet(id, conn, getProductsSql);
		while (resultSet.next()) {
			Product product = new BinaryProduct(resultSet.getInt("id"), resultSet.getString("binaryProduct.name"),
					true, null);
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

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getAllLiquidProducts(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getAllCountableProducts(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getAllUncountableProducts(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getAllBinaryProducts(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	private Collection<? extends Product> getAllLiquidProducts(Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String getProductsSql = "SELECT * FROM liquidProduct, liquidProductCategory "
				+ "WHERE liquidProduct.liquidProductCategoryId=liquidProductCategory.id";
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString("liquidProductCategory.name");
			Product product = new LiquidProduct(resultSet.getInt("liquidProduct.id"), resultSet.getString("name"),
					Double.MAX_VALUE, ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getAllCountableProducts(Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String getProductsSql = "SELECT * FROM countableProduct, countableProductCategory "
				+ "WHERE countableProduct.countableProductCategoryId=countableProductCategory.id";
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString("countableProductCategory.name");
			Product product = new CountableProduct(resultSet.getInt("countableProduct.id"), resultSet.getString("name"),
					Integer.MAX_VALUE, ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getAllUncountableProducts(Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String getProductsSql = "SELECT * FROM uncountableProduct, uncountableProductCategory "
				+ "WHERE uncountableProduct.uncountableProductCategoryId=uncountableProductCategory.id";
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString("uncountableProductCategory.name");
			Product product = new UncountableProduct(resultSet.getInt("uncountableProduct.id"), resultSet.getString("uncountableProduct.name"),
					Double.MAX_VALUE, ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getAllBinaryProducts(Connection conn) throws SQLException { //kolekcja, która w trybie generycznym przyjmuje obiekty, które dziedziczą po klasie Product lub obiekty klasy Product
		List<Product> products = new ArrayList<>();
		String getProductsSql = "SELECT * FROM binaryProduct, binaryProductCategory "
				+ "WHERE binaryProduct.binaryProductCategoryId=binaryProductCategory.id";
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString("binaryProductCategory.name");
			Product product = new BinaryProduct(resultSet.getInt("binaryProduct.id"), resultSet.getString("binaryProduct.name"),
					true, ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	@Override
	public boolean addProductForHousehold(Product addedProduct, int householdId, String tableName) {
		String productHouseholdName = tableName+"Household";
		String attributes = "(?, ?, ?)";
		if(tableName=="binaryProduct"){
			attributes = "(?, ?)";
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("INSERT INTO "+productHouseholdName+" VALUES"+attributes);
			prepareStatement.setInt(1, addedProduct.getId());
			prepareStatement.setInt(2, householdId);
			if(addedProduct instanceof LiquidProduct){
			prepareStatement.setDouble(3, ((LiquidProduct)addedProduct).getVolume()); //rzutowanie na liquid product zeby dostac sie do metod z klasy liquidProduct
			}
			else if(addedProduct instanceof CountableProduct){
				prepareStatement.setInt(3,  ((CountableProduct)addedProduct).getAmount());
			}
			else if(addedProduct instanceof UncountableProduct){
				prepareStatement.setDouble(3, ((UncountableProduct)addedProduct).getWeight());
			}
			int executeUpdate = prepareStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> getProductsByHouseholdId(int id) {
		List<Product> products = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getCountableProductsByHouseholdId(id,conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getUncountableProductsByHouseholdId(id,conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getLiquidProductsByHouseholdId(id,conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			products.addAll(getBinaryProductsByHouseholdId(id,conn));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	private Collection<? extends Product> getCountableProductsByHouseholdId(int id, Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String tableName = "countableProduct";
		String getProductsSql = "SELECT * FROM "+tableName+", "+tableName+"Household, "+tableName+"Category "
				+ "WHERE "+tableName+"Household.householdId=? AND "+tableName+".id="+tableName+"Household."+tableName+"Id "
				+ "AND "+tableName+"Category.id="+tableName+"."+tableName+"CategoryId";
		String unit = "amount";
		
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		prepareStatement.setInt(1, id);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString(""+tableName+"Category.name");
			Product product = new CountableProduct(resultSet.getInt("id"), resultSet.getString(""+tableName+".name"),
					resultSet.getInt(""+tableName+"Household."+unit), ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getUncountableProductsByHouseholdId(int id, Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String tableName = "uncountableProduct";
		String getProductsSql = "SELECT * FROM "+tableName+", "+tableName+"Household, "+tableName+"Category "
				+ "WHERE "+tableName+"Household.householdId=? AND "+tableName+".id="+tableName+"Household."+tableName+"Id "
				+ "AND "+tableName+"Category.id="+tableName+"."+tableName+"CategoryId";
		String unit = "weight";
		
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		prepareStatement.setInt(1, id);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString(""+tableName+"Category.name");
			Product product = new UncountableProduct(resultSet.getInt("id"), resultSet.getString(""+tableName+".name"),
					resultSet.getDouble(""+tableName+"Household."+unit), ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getLiquidProductsByHouseholdId(int id, Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String getProductsSql = "SELECT * FROM liquidProduct, liquidProductHousehold, liquidProductCategory "
				+ "WHERE liquidProductHousehold.householdId=? AND liquidProduct.id=liquidProductHousehold.liquidProductId "
				+ "AND liquidProductCategory.id=liquidProduct.liquidProductCategoryId";
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		prepareStatement.setInt(1, id);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString("liquidProductCategory.name");
			Product product = new LiquidProduct(resultSet.getInt("id"), resultSet.getString("liquidProduct.name"),
					resultSet.getDouble("liquidProductHousehold.volume"), ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	private Collection<? extends Product> getBinaryProductsByHouseholdId(int id, Connection conn) throws SQLException {
		List<Product> products = new ArrayList<>();
		String tableName = "binaryProduct";
		String getProductsSql = "SELECT * FROM "+tableName+", "+tableName+"Household, "+tableName+"Category "
				+ "WHERE "+tableName+"Household.householdId=? AND "+tableName+".id="+tableName+"Household."+tableName+"Id "
				+ "AND "+tableName+"Category.id="+tableName+"."+tableName+"CategoryId";
		
		PreparedStatement prepareStatement = conn.prepareStatement(getProductsSql);
		prepareStatement.setInt(1, id);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String categoryName = resultSet.getString(""+tableName+"Category.name");
			Product product = new BinaryProduct(resultSet.getInt("id"), resultSet.getString(""+tableName+".name"),true,
					 ProductCategoryEnum.createEnumForName(categoryName));
			products.add(product);
		}
		return products;
	}

	@Override
	public boolean remove(Product selectedProduct, int householdId) {
		String tableName = selectedProduct.getTableName();
		try (Connection conn = DriverManager.getConnection(COOOKIDEA_CONNECTION_STRING)) {
			PreparedStatement prepareStatement = conn.prepareStatement("DELETE FROM "+tableName+"Household WHERE householdId=? "
					+ "AND "+tableName+"Id=?");
			prepareStatement.setInt(1, householdId);
			prepareStatement.setInt(2, selectedProduct.getId());
			int executeUpdate = prepareStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
}