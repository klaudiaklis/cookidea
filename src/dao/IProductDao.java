package dao;

import java.util.List;

import model.Product;

public interface IProductDao {

	List<Product> getProductsByRecipeId(int id);
	
	List<Product> getProductsByHouseholdId(int id);

	List<Product> getAllProducts();

	boolean addProductForHousehold(Product addedProduct, int householdId, String tableName);

	boolean remove(Product selectedProduct, int householdId);

}
