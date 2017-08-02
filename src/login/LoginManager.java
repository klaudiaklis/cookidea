package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;

  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
   */ 
  public void authenticated(String sessionID) {
    showMainView(sessionID);
  }

  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("login.fxml")
      );
      scene.setRoot((Parent) loader.load());
      LoginController controller = 
        loader.<LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void showMainView(String sessionID)  {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("mainview.fxml")
      );
      scene.setRoot((Parent) loader.load());
      try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (InstantiationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IllegalAccessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      String result = "";
      try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookidea?"+"user=root&password=root")) {
    	  PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM countableproduct");
    	  ResultSet executeQuery = prepareStatement.executeQuery();
    	  while (executeQuery.next()) {
			result = executeQuery.getString(1);
		}
      } catch (SQLException e) {
		e.printStackTrace();
	}
      MainViewController controller = 
        loader.<MainViewController>getController();
      controller.initSessionID(this, sessionID, result);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}