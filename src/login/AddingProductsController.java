package login;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import model.ProductCategoryEnum;

/**
 * Controls the adding products screen.
 * @author Klaudia
 */
public class AddingProductsController {
	@FXML private Button doneButton;
	@FXML private Button cancelButton;
	@FXML private Label householdProductsLabel;
	@FXML private ListView<String> householdProductsList;
	@FXML private TilePane vegetablesTilePane;
	
	private List<String> products = new ArrayList<>();
	private ListProperty<String> listProperty = new SimpleListProperty<>();

	public void initView(LoginManager loginManager, String user) {
		householdProductsLabel.setText(householdProductsLabel.getText() + " " + user);
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
			
		});
		products.add("apple: " + 3);
		listProperty.set(FXCollections.observableArrayList(products));
		householdProductsList.itemsProperty().bind(listProperty);
		
		initializeTilePane(ProductCategoryEnum.VEGETABLES);
	}
	
	private void initializeTilePane(ProductCategoryEnum productCategory) {
		TilePane tilePane = getTilePaneForProductCategory(productCategory);
		tilePane.setPadding(new Insets(5,0,5,0));
		tilePane.setVgap(4);
		tilePane.setHgap(4);
		tilePane.setPrefColumns(2);
		tilePane.setStyle("-fx-background-color: DAE6F3;");
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ImageView pages[] = new ImageView[8];
		for (int i=0; i<8; i++) {
		     pages[i] = new ImageView(
		        new Image(classLoader.getResourceAsStream(
		        "wx_66.png")));
		     tilePane.getChildren().add(pages[i]);
		}
	}
	
	private TilePane getTilePaneForProductCategory(ProductCategoryEnum productCategory) {
		switch (productCategory) {
		case VEGETABLES:
			return vegetablesTilePane;
		default:
			assert false : "Unrecognized category!";
			return null;
		}
	}
	
}
