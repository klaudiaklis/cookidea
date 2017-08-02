package login;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  
  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String user = authorize();
        if (user != null) {
          loginManager.authenticated(user);
        }
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a username for the authorized user
   * otherwise, return null.
   */   
  private String authorize() {
    return 
      "open".equals(user.getText()) && "sesame".equals(password.getText()) 
            ? user.getText() 
            : null;
  }
}
