package login;

import dao.IHouseholdDao;
import allerts.Alerts;
import dao.HouseholdDao;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Household;
import security.Md5;

/** Controls the login screen */
public class LoginController {
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Hyperlink registerButton;

	public void initManager(final LoginManager loginManager) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Household user = authorize();
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
	private Household authorize() {
		IHouseholdDao userDao = new HouseholdDao();
		Household householdByUser = userDao.getHouseholdByName(user.getText());
		if (householdByUser == null) {
			Alerts.errorAlert("Wrong credentialls!");
			return null;
		}
		boolean credentiallsOk = householdByUser.getName().equals(user.getText())
				&& householdByUser.getPassword().equals(Md5.hash(password.getText()));
		if (!credentiallsOk) {
			Alerts.errorAlert("Wrong credentialls!");
		}
		return credentiallsOk ? householdByUser : null;
	}
}
