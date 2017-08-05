package model;

public abstract class Product {
	private int id;
	private String name;
	public Product(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public abstract String getAmountString();
	
	@Override
	public String toString() {
		return name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
