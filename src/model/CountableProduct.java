package model;

public class CountableProduct extends Product {
	private int amount;
	public CountableProduct(int id, String name, int amount, ProductCategoryEnum category) {
		super(id, name, category);
		this.amount = amount;
	}
	public int getAmount() {
		return amount;
	}
	@Override
	public String getAmountString() {
		return "" + amount;
	}
	@Override
	public String getTableName() {
		return "countableProduct";
	}
}
