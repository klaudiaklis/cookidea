package dao;

import java.util.List;

import model.Product;

public class ProductDao implements IProductDao {

	private IDatabaseDao databaseDao;

	public ProductDao() {
		databaseDao = new DatabaseDao();
	}

	@Override
	public List<Product> getProductsByRecipeId(int id) {
		return databaseDao.getProductsByRecipeId(id);
	}

	@Override
	public List<Product> getAllProducts() {
		return databaseDao.getAllProducts();
	}

	@Override
	public boolean addProductForHousehold(Product addedProduct, int householdId, String tableName) {
		return databaseDao.addProductForHousehold(addedProduct, householdId, tableName);
	}

	@Override
	public List<Product> getProductsByHouseholdId(int id) {
		return databaseDao.getProductsByHouseholdId(id);
	}

	@Override
	public boolean remove(Product selectedProduct, int householdId) {
		return databaseDao.remove(selectedProduct, householdId);
	}

}
