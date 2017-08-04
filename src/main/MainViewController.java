package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import login.LoginManager;

public class MainViewController {
	@FXML
	private Button logoutButton;
	@FXML
	private Button modifyButton;

	public void initView(LoginManager loginManager) {
		
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

}
