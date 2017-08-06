package model;

public abstract class Product {
	private int id;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private String name;
	private ProductCategoryEnum category;
	
	public Product(int id, String name, ProductCategoryEnum category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	public abstract String getAmountString();
	public abstract String getTableName();
	@Override
	public String toString() {
		return name + "\t" +getAmountString();
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public ProductCategoryEnum getCategory() {
		return category;
	}
}
