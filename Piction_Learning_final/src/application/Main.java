package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
 
 
public class Main extends Application {
	static public int choice=0;
	static public String today_Date = "2016-11-20";
	static public String selected_date=today_Date;
	static public String keyboard_temp="";
	static public LoginController controller;
	static public int keyboard_flag=0;
	static public int id_index=0;
	
    @Override
    public void start(Stage primaryStage) {
        try {
        	ClientManager connection = new ClientManager();
        	connection.connect();
        	
        	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
            Parent root = (Parent)myLoader.load();
            
            controller = (LoginController) myLoader.getController();
            controller.setPrevStage(primaryStage);
            controller.setBackgroundImage();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style/login.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}