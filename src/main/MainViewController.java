package main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.CousineTypeDao;
import dao.DifficultyLevelDao;
import dao.ICousineTypeDao;
import dao.IDifficultyLevelDao;
import dao.IMealTypeDao;
import dao.IRecipeDao;
import dao.MealTypeDao;
import dao.RecipeDao;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import login.LoginManager;
import model.CousineType;
import model.DifficultyLevel;
import model.MealType;
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

	public void initView(LoginManager loginManager) {
		intializeButtons(loginManager);
		initializeComboboxes();
		initializeRecipes(loginManager);
	}

	private void initializeRecipes(LoginManager loginManager) {
		IRecipeDao dao = new RecipeDao();
		List<Recipe> recipes = dao.getAllRecipes();
		recipesTilePane.setPadding(new Insets(5, 0, 5, 0));
		recipesTilePane.setVgap(4);
		recipesTilePane.setHgap(4);
		recipesTilePane.setPrefColumns(2);
		recipesTilePane.setStyle("-fx-background-color: DAE6F3;");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ImageView pages[] = new ImageView[4];
		for (int i = 0; i < 4; i++) {
			Recipe recipe = recipes.get(i);
			String name = recipe.getName();
			pages[i] = new ImageView(new Image(classLoader.getResourceAsStream("recipe_" + name + ".png")));
			ImageView recipeImage = pages[i];
			recipesTilePane.getChildren().add(recipeImage);
			recipeImage.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe/recipeView.fxml"));
						loginManager.getScene().setRoot((Parent) loader.load());
						RecipeViewController controller = loader.<RecipeViewController>getController();
						controller.initView(recipe,recipeImage, loginManager);
					} catch (IOException ex) {
						Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			});
		}
	}

	private void intializeButtons(LoginManager loginManager) {
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
	}

	private void initializeCousineTypeCombobox() {
		ICousineTypeDao dao = new CousineTypeDao();
		List<CousineType> cousineTypes = dao.getAllCousineTypes();
		cousineTypeCombobox.getItems().setAll(cousineTypes);
	}

	private void initializeMealTypeCombobox() {
		IMealTypeDao dao = new MealTypeDao();
		List<MealType> mealTypes = dao.getAllMealTypes();
		mealTypeCombobox.getItems().setAll(mealTypes);
	}
}