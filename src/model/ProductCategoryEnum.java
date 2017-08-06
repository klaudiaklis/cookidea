package model;

public enum ProductCategoryEnum {
	VEGETABLES, FRUITS, ALCOHOLS, BREAD, DAIRY, GRAIN_PRODUCTS, MEATS, OILS, SPICES, SWEETS, FISH, SAUCE;
	
	public static ProductCategoryEnum createEnumForName(String name) {
		ProductCategoryEnum category = null;
		switch (name) {
		case "Vegetables":
			category = ProductCategoryEnum.VEGETABLES;
			break;
		case "Fruits":
			category = ProductCategoryEnum.FRUITS;
			break;
		case "Dairy":
			category = ProductCategoryEnum.DAIRY;
			break;
		case "Bread":
			category = ProductCategoryEnum.BREAD;
			break;
		case "Sweets":
			category = ProductCategoryEnum.SWEETS;
			break;
		case "Meats":
			category = ProductCategoryEnum.MEATS;
			break;
		case "Fish":
			category = ProductCategoryEnum.FISH;
			break;
		case "Grain products":
			category = ProductCategoryEnum.GRAIN_PRODUCTS;
			break;
		case "Alcohols":
			category = ProductCategoryEnum.ALCOHOLS;
			break;
		case "Oils":
			category = ProductCategoryEnum.OILS;
			break;
		case "Spices":
			category = ProductCategoryEnum.SPICES;
			break;
		case "Sauce":
			category = ProductCategoryEnum.SAUCE;
			break;
			
		default:
			break;
		}
		return category;
	}
}
