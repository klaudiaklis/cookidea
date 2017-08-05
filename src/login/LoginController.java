package login;

import dao.IHouseholdDao;
import dao.HouseholdDao;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Household;

/** Controls the login screen */
public class LoginController {
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;

	public void initialize() {
	}

	public void initManager(final LoginManager loginManager) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String user = authorize();
				if (user != null) {
					loginManager.authenticated(user);
				}
			}
		});

		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginManager.register();
			}

		});
	}

	/**
	 * Check authorization credentials.
	 * 
	 * If accepted, return a username for the authorized user otherwise, return
	 * null.
	 */
	private String authorize() {
		IHouseholdDao userDao = new HouseholdDao();
		Household householdByUser = userDao.getHouseholdByName(user.getText());
		if (householdByUser == null) {
			return null;
		}
		return householdByUser.getName().equals(user.getText())
				&& householdByUser.getPassword().equals(password.getText()) ? user.getText() : null;
	}
}
