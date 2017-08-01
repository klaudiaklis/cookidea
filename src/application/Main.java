package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.LoginManager;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
		    Scene scene = new Scene(new StackPane());
		    
		    LoginManager loginManager = new LoginManager(scene);
		    loginManager.showLoginScreen();

		    stage.setScene(scene);
		    stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
