package addingproduct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dao.IProductDao;
import dao.ProductDao;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginManager;
import main.MainViewController;
import model.BinaryProduct;
import model.CountableProduct;
import model.Household;
import model.LiquidProduct;
import model.Product;
import model.ProductCategoryEnum;
import model.UncountableProduct;

/**
 * Controls the adding products screen.
 * 
 * @author Klaudia
 */
public class AddingProductsController {
	@FXML
	private Button doneButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Label householdProductsLabel;
	@FXML
	private ListView<Product> householdProductsList;
	@FXML
	private TilePane vegetablesTilePane;
	@FXML
	private TilePane fruitsTilePane;
	@FXML
	private TilePane alcoholsTilePane;
	@FXML
	private TilePane breadTilePane;
	@FXML
	private TilePane dairyTilePane;
	@FXML
	private TilePane grainProductsTilePane;
	@FXML
	private TilePane meatsTilePane;
	@FXML
	private TilePane oilsTilePane;
	@FXML
	private TilePane spicesTilePane;
	@FXML
	private TilePane sweetsTilePane;
	@FXML
	private TilePane sauceTilePane;
	@FXML
	private TilePane fishTilePane;

	private List<Product> productsInHousehold = new ArrayList<>();
	private ListProperty<Product> listProperty = new SimpleListProperty<>();
	private List<Product> allProductsInDB;
	private IProductDao productDao;
	private LoginManager loginManager;

	public void initView(LoginManager loginManager, String user) {
		this.loginManager = loginManager;
		householdProductsLabel.setText(householdProductsLabel.getText() + " " + user);
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}

		});
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showMainView(loginManager);
			}

		});
		productDao = new ProductDao();
		allProductsInDB = productDao.getAllProducts();

		productsInHousehold.addAll(productDao.getProductsByHouseholdId(loginManager.getHousehold().getId()));
		refreshProductsList();

		householdProductsList.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (KeyCode.DELETE.equals(event.getCode())) { 
					Product selectedProduct = householdProductsList.getSelectionModel().getSelectedItem();
					productsInHousehold.remove(selectedProduct);
					productDao.remove(selectedProduct, loginManager.getHousehold().getId());
					refreshProductsList();
				}
			}
		});
		
		for (ProductCategoryEnum category : ProductCategoryEnum.values()) {
			initializeTilePane(category);
		}
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

	private void initializeTilePane(ProductCategoryEnum productCategory) {
		TilePane tilePane = getTilePaneForProductCategory(productCategory);
		tilePane.setPadding(new Insets(5, 0, 5, 0));
		tilePane.setVgap(4);
		tilePane.setHgap(4);
		tilePane.setPrefColumns(2);
		tilePane.setStyle("-fx-background-color: DAE6F3;");

		List<Product> filteredProducts = allProductsInDB.stream().filter(p -> productCategory.equals(p.getCategory()))
				.collect(Collectors.toList()); // wyrazenie lambda, filtruje
												// tylko te produkty, ktore maja
												// odpowiedni
												// productCategoryEnum
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ImageView images[] = new ImageView[filteredProducts.size()];
		for (int i = 0; i < filteredProducts.size(); i++) {
			Product product = filteredProducts.get(i);
			images[i] = new ImageView(new Image(classLoader
					.getResourceAsStream(product.getCategory().toString() + "_" + product.getName() + ".jpg")));
			tilePane.getChildren().add(images[i]);
			addHandlingToImage(images[i], product);
		}
	}

	private void addHandlingToImage(ImageView image, Product product) {
		image.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				amountDeterminingPopup(product);
			}

			private void amountDeterminingPopup(Product product) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				VBox dialogVbox = new VBox(20);
				dialogVbox.getChildren().add(new Text("Adding " + product.getName()));
				TextField howManyTextField = new TextField("");
				Label unitLabel = new Label(getLabelProductType(product));
				dialogVbox.getChildren().add(new FlowPane(howManyTextField, unitLabel));
				Button okButton = new Button("Ok");
				dialogVbox.getChildren().add(okButton);
				Scene dialogScene = new Scene(dialogVbox, 300, 200);
				dialog.setScene(dialogScene);
				okButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						addProductToHousehold(product, howManyTextField.getText(), loginManager.getHousehold());
						dialog.close();
					}
				});
				dialog.show();
			}

			private void addProductToHousehold(Product product, String howMany, Household household) {
				Product addedProduct = product;
				String productTableName = "";
				if (product instanceof BinaryProduct) {
					addedProduct = new BinaryProduct(product.getId(), product.getName(), true, product.getCategory());
					productTableName = "binaryProduct";
				} else if (product instanceof LiquidProduct) {
					addedProduct = new LiquidProduct(product.getId(), product.getName(), Double.valueOf(howMany),
							product.getCategory());
					productTableName = "liquidProduct";
				} else if (product instanceof UncountableProduct) {
					addedProduct = new UncountableProduct(product.getId(), product.getName(), Double.valueOf(howMany),
							product.getCategory());
					productTableName = "uncountableProduct";
				} else if (product instanceof CountableProduct) {
					addedProduct = new CountableProduct(product.getId(), product.getName(), Integer.valueOf(howMany),
							product.getCategory());
					productTableName = "countableProduct";
				}
				productsInHousehold.add(addedProduct);
				productDao.addProductForHousehold(addedProduct,household.getId(), productTableName );
				refreshProductsList();
			}
		});
	}


	protected void refreshProductsList() {
		listProperty.set(FXCollections.observableArrayList(productsInHousehold));
		householdProductsList.itemsProperty().bind(listProperty);
	}
	
	protected String getLabelProductType(Product product) {
		if (product instanceof BinaryProduct) {
			return "";
		} else if (product instanceof LiquidProduct) {
			return " ml";
		} else if (product instanceof UncountableProduct) {
			return " g";
		}
		return " szt";
	}

	private TilePane getTilePaneForProductCategory(ProductCategoryEnum productCategory) {
		switch (productCategory) {
		case VEGETABLES:
			return vegetablesTilePane;
		case FRUITS:
			return fruitsTilePane;
		case ALCOHOLS:
			return alcoholsTilePane;
		case BREAD:
			return breadTilePane;
		case DAIRY:
			return dairyTilePane;
		case GRAIN_PRODUCTS:
			return grainProductsTilePane;
		case MEATS:
			return meatsTilePane;
		case OILS:
			return oilsTilePane;
		case SPICES:
			return spicesTilePane;
		case SWEETS:
			return sweetsTilePane;
		case FISH:
			return fishTilePane;
		case SAUCE:
			return sauceTilePane;
		default:
			assert false : "Unrecognized category!";
			return null;
		}
	}
}