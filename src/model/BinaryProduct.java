package model;

public class BinaryProduct extends Product {
	private boolean hasGot;

	public BinaryProduct(int id, String name, boolean hasGot) {
		super(id, name);
		this.hasGot = hasGot;
	}

	public boolean isHasGot() {
		return hasGot;
	}

	@Override
	public String getAmountString() {
		return "";
	}
}
