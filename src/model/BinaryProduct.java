package model;

public class BinaryProduct extends Product {
	private boolean hasGot;

	public BinaryProduct(int id, String name, boolean hasGot, ProductCategoryEnum category) {
		super(id, name, category);
		this.hasGot = hasGot;
	}

	public boolean isHasGot() {
		return hasGot;
	}

	@Override
	public String getAmountString() {
		return "";
	}

	@Override
	public String getTableName() {
		return "binaryProduct";
	}
}
