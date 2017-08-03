package register;

import allerts.Alerts;
import dao.IUserDao;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.LoginManager;

/**
 * Controls the register screen
 * 
 * @author Klaudia
 */
public class RegisterController {
	@FXML
	private TextField user;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField password1;
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;
	private IUserDao userDao;

	public void initView(LoginManager loginManager) {
		userDao = new UserDao();
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
		});

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (isNotAllDataFilledIn()) {
					alertAboutNotAllDataFilledIn();
				} else if (arePasswordsDifferent()) {
					alertAboutDifferentPasswords();
				} else {
					userDao.registerUser(user.getText(), password.getText());
				}
			}
		});
	}

	protected boolean isNotAllDataFilledIn() {
		return user.getText() == null || user.getText().isEmpty() || password == null || password.getText() == null
				|| password.getText().isEmpty() || password1 == null || password1.getText() == null || password1.getText().isEmpty();
	}

	protected void alertAboutNotAllDataFilledIn() {
		String allertContent = "Not all data filled in!";
		Alerts.errorAlert(allertContent);
	}

	protected boolean arePasswordsDifferent() {
		return !password.getText().equals(password1.getText());
	}

	protected void alertAboutDifferentPasswords() {
		String allertContent = "Passwords are not equal!";
		Alerts.errorAlert(allertContent);
	}
}