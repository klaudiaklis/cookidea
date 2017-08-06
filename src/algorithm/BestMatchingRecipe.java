package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import model.Product;
import model.Recipe;

public class BestMatchingRecipe {
	
	private static Comparator<Recipe> comparator = new Comparator<Recipe>(){

		@Override
		public int compare(Recipe r1, Recipe r2) {
			if (r1.getMatchingCounter() > r2.getMatchingCounter()) {
				return -1;
			} else if(r1.getMatchingCounter() < r2.getMatchingCounter()) {
				return 1;
			}
			return 0;
		}
	};
	
	public static List<Recipe> getBestRecipes(List<Product> householdProducts, List<Recipe> recipes) {
		List<Recipe> prioritizedRecipes = new ArrayList<>();
		for (Recipe recipe : recipes) {
			recipe.clearCounter();
			for (Product product : householdProducts) {
				if (recipe.getProducts().contains(product)) {
					recipe.increaseCounter();
				}
			}
			prioritizedRecipes.add(recipe);
		}
		Collections.sort(prioritizedRecipes,comparator);
		prioritizedRecipes = prioritizedRecipes.stream().filter(r -> r.getMatchingCounter() > 0).collect(Collectors.toList());
		return prioritizedRecipes;
	}
}
