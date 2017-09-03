package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import algorithm.BestMatchingRecipe;
import dao.CousineTypeDao;
import dao.DifficultyLevelDao;
import dao.ICousineTypeDao;
import dao.IDifficultyLevelDao;
import dao.IMealTypeDao;
import dao.IRecipeDao;
import dao.MealTypeDao;
import dao.ProductDao;
import dao.RecipeDao;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import login.LoginManager;
import model.CousineType;
import model.DifficultyLevel;
import model.MealType;
import model.Product;
import model.Recipe;
import recipe.RecipeViewController;

public class MainViewController {
	@FXML
	private Button logoutButton;
	@FXML
	private Button modifyButton;
	@FXML
	private ComboBox<MealType> mealTypeCombobox;
	@FXML
	private ComboBox<CousineType> cousineTypeCombobox;
	@FXML
	private ComboBox<DifficultyLevel> difficultyLevelCombobox;
	@FXML
	private TilePane recipesTilePane;
	@FXML
	private ListView<Product> productsListView;

	private List<Product> productsInHousehold = new ArrayList<>();
	private ListProperty<Product> listProperty = new SimpleListProperty<>();
	private ProductDao productDao;
	private LoginManager loginManager;
	private List<Recipe> recipes;
	private CousineType currentSelectedCousineType = null;
	private MealType currentSelectedMealType = null;
	private DifficultyLevel currentSelectedDifficultyLevel = null;

	public void initView(LoginManager loginManager) {
		this.loginManager = loginManager;
		intializeButtons();
		initializeComboboxes();
		initializeProductsInHouseholdList();
		initializeRecipes();
	}

	private void initializeProductsInHouseholdList() {
		productDao = new ProductDao();
		productsInHousehold.addAll(productDao.getProductsByHouseholdId(loginManager.getHousehold().getId()));
		listProperty.set(FXCollections.observableArrayList(productsInHousehold));
		productsListView.itemsProperty().bind(listProperty);
	}

	private void initializeRecipes() {
		IRecipeDao dao = new RecipeDao();
		recipes = dao.getAllRecipes();
		recipesTilePane.setPadding(new Insets(5, 0, 5, 0));
		recipesTilePane.setVgap(4);
		recipesTilePane.setHgap(4);
		recipesTilePane.setPrefColumns(2);
		recipesTilePane.setStyle("-fx-background-color: DAE6F3;");

		loadRecipes(recipes);
	}

	private void loadRecipes(List<Recipe> recipes) {
		recipesTilePane.getChildren().clear();
		List<Recipe> prioritizedRecipes = BestMatchingRecipe.getBestRecipes(productsInHousehold, recipes);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ImageView pages[] = new ImageView[prioritizedRecipes.size()];
		for (int i = 0; i < prioritizedRecipes.size(); i++) {
			Recipe recipe = prioritizedRecipes.get(i);
			String name = recipe.getName();
			pages[i] = new ImageView(new Image(classLoader.getResourceAsStream("recipe_" + name + ".png")));
			ImageView recipeImage = pages[i];
			recipesTilePane.getChildren().add(recipeImage);
			recipeImage.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe/recipeView2.fxml"));
						loginManager.getScene().setRoot((Parent) loader.load());
						RecipeViewController controller = loader.<RecipeViewController>getController();
						controller.initView(recipe, recipeImage, loginManager);
					} catch (IOException ex) {
						Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			});
		}
	}

	private void intializeButtons() {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
		});

		modifyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginManager.authenticated();
			}
		});
	}

	private void initializeComboboxes() {
		initializeMealTypeCombobox();
		initializeCousineTypeCombobox();
		initializeDifficultyLevelCombobox();
	}

	private void initializeDifficultyLevelCombobox() {
		IDifficultyLevelDao dao = new DifficultyLevelDao();
		List<DifficultyLevel> difficultyLevels = dao.getAllDifficultyLevels();
		difficultyLevelCombobox.getItems().setAll(difficultyLevels);
		difficultyLevelCombobox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DifficultyLevel selectedDifficultyLevel = difficultyLevelCombobox.getSelectionModel().getSelectedItem();
				currentSelectedDifficultyLevel = selectedDifficultyLevel;
				filterRecipesByDifficultyLevel(selectedDifficultyLevel);
			}

		});
	}

	protected void filterRecipesByDifficultyLevel(DifficultyLevel selectedDifficultyLevel) {
		List<Recipe> filteredRecipes = recipes.stream()
				.filter(r -> selectedDifficultyLevel.getId() == r.getDifficultyLevelId()).collect(Collectors.toList());
		if (currentSelectedCousineType != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedCousineType.getId() == r.getCousineTypeId()).collect(Collectors.toList());
		}
		if (currentSelectedMealType != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedMealType.getId() == r.getMealTypeId()).collect(Collectors.toList());
		}
		loadRecipes(filteredRecipes);
	}

	private void initializeCousineTypeCombobox() {
		ICousineTypeDao dao = new CousineTypeDao();
		List<CousineType> cousineTypes = dao.getAllCousineTypes();
		cousineTypeCombobox.getItems().setAll(cousineTypes);
		cousineTypeCombobox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CousineType selectedCousineType = cousineTypeCombobox.getSelectionModel().getSelectedItem();
				currentSelectedCousineType = selectedCousineType;
				filterRecipesByCousineType(selectedCousineType);
			}
		});
	}

	protected void filterRecipesByCousineType(CousineType selectedCousineType) {
		List<Recipe> filteredRecipes = recipes.stream()
				.filter(r -> selectedCousineType.getId() == r.getCousineTypeId()).collect(Collectors.toList());
		if (currentSelectedDifficultyLevel != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedDifficultyLevel.getId() == r.getDifficultyLevelId()).collect(Collectors.toList());
		}
		if (currentSelectedMealType != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedMealType.getId() == r.getMealTypeId()).collect(Collectors.toList());
		}
		loadRecipes(filteredRecipes);
	}

	private void initializeMealTypeCombobox() {
		IMealTypeDao dao = new MealTypeDao();
		List<MealType> mealTypes = dao.getAllMealTypes();
		mealTypeCombobox.getItems().setAll(mealTypes);
		mealTypeCombobox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MealType selectedMealType = mealTypeCombobox.getSelectionModel().getSelectedItem();
				currentSelectedMealType = selectedMealType;
				filterRecipesByMealType(selectedMealType);
			}
		});
	}

	protected void filterRecipesByMealType(MealType selectedMealType) {
		List<Recipe> filteredRecipes = recipes.stream()
				.filter(r -> selectedMealType.getId() == r.getMealTypeId()).collect(Collectors.toList());
		if (currentSelectedCousineType != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedCousineType.getId() == r.getCousineTypeId()).collect(Collectors.toList());
		}
		if (currentSelectedDifficultyLevel != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(r -> currentSelectedDifficultyLevel.getId() == r.getDifficultyLevelId()).collect(Collectors.toList());
		}
		loadRecipes(filteredRecipes);
	}
}