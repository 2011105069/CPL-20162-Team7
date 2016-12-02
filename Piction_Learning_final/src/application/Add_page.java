package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Add_page {
	private Stage primaryStage;

	public Add_page() {
		try {			
			primaryStage = new Stage();
			
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/add_member.fxml"));
			Parent root = (Parent)myLoader.load();
			
			AddMemberController controller = (AddMemberController) myLoader.getController();
            controller.setPrevStage(primaryStage);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("style/popup.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
