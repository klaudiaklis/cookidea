package login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import register.RegisterController;

/** Manages control flow for logins */
public class LoginManager {
	private Scene scene;

	public LoginManager(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated.
	 * Will show the adding products screen.
	 */
	public void authenticated(String user) {
		showAddingProductsView(user);
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void showAddingProductsView(String user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddingProductsView.fxml"));
			scene.setRoot((Parent) loader.load());
			AddingProductsController controller = loader.<AddingProductsController>getController();
			controller.initView(this, user);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void register() {
		showRegisterView();
	}

	private void showRegisterView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/register/register.fxml"));
			scene.setRoot((Parent) loader.load());
			RegisterController controller = loader.<RegisterController>getController();
			controller.initView(this);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}