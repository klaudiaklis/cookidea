package allerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static void errorAlert(String allertContent) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(allertContent);
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				alert.close();
			}
		});
	}
}
