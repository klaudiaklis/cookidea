package recipe;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.IProductDao;
import dao.ProductDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import login.LoginManager;
import main.MainViewController;
import model.Product;
import model.Recipe;

public class RecipeViewController {
	@FXML
	private Button cancelButton;
	@FXML
	private Button printShopListButton;
	@FXML
	private Button printRecipeList;
	@FXML
	private Label recipeLabelName;
	@FXML
	private Label difficultyLevelLabel;
	@FXML
	private Label portionsLabel;
	@FXML
	private Label durationLabel;
	@FXML
	private ImageView recipeImageView;
	@FXML
	private TextArea ingredientsTextArea;
	@FXML
	private TextArea descriptionTextArea;
	
	private static Map<Integer,String> difficultyLevelMapper = new HashMap<>();
	static {
		difficultyLevelMapper.put(Integer.valueOf(1), "Easy");
		difficultyLevelMapper.put(Integer.valueOf(2), "Medium");
		difficultyLevelMapper.put(Integer.valueOf(3), "Hard");
	}
	
	public void initView(Recipe recipe, ImageView recipeImage, LoginManager loginManager) {
		recipeImageView.setImage(recipeImage.getImage());
		recipeLabelName.setText(recipe.getName());
		recipeLabelName.setFont(Font.font(32.0));
		String difficulty = difficultyLevelMapper.get(recipe.getDifficultyLevelId());
		difficultyLevelLabel.setText("Stopień trudności: "+difficulty);
		portionsLabel.setText("Liczba porcji: " + String.valueOf(recipe.getPortions()));
		durationLabel.setText("Czas przygotowania: "+String.valueOf(recipe.getDuration()) + " min");
		String ingredients = getIngredientsForRecipe(recipe.getId());
		ingredientsTextArea.setText(ingredients );
		descriptionTextArea.setText(recipe.getDescription());
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showMainView(loginManager);
			}
		});
	}

	protected void showMainView(LoginManager loginManager) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/mainView.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			MainViewController controller = loader.<MainViewController>getController();
			controller.initView(loginManager);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private String getIngredientsForRecipe(int id) {
		IProductDao ingredientDao = new ProductDao();
		List<Product> products = ingredientDao.getProductsByRecipeId(id);
		
		String ingredientsString = "";
		for (Product product : products) {
			ingredientsString += product.getName() + "\t" +product.getAmountString() + "\n";
		}
		return ingredientsString;
	}

}
