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

}
