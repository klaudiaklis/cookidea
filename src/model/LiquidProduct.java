package model;

public class LiquidProduct extends Product {
	private double volume;
	public LiquidProduct(int id, String name, double volume, ProductCategoryEnum category) {
		super(id, name, category);
		this.volume = volume;
	}
	public double getVolume() {
		return volume;
	}
	@Override
	public String getAmountString() {
		return "" + volume + " ml";
	}
	@Override
	public String getTableName() {
		return "liquidProduct";
	}
	
	
}
