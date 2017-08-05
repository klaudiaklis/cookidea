package dao;

import java.util.List;

import model.Product;

public interface IProductDao {

	List<Product> getProductsByRecipeId(int id);

}
