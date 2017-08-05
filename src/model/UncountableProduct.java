package model;

public class UncountableProduct extends Product {
	private double weight;
	public UncountableProduct(int id, String name, double weight) {
		super(id, name);
		this.weight = weight;
	}
	public double getWeight() {
		return weight;
	}
	@Override
	public String getAmountString() {
		return "" + weight + " g";
	}
}
