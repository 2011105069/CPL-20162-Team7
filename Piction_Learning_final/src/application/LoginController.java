package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class LoginController {
	
	Stage prevStage;
	Stage keyboardStage;
	String temp_password = "4321";

    @FXML private Label lblStatus;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private GridPane main_grid;
    @FXML private AnchorPane member01pane, login_background;
    @FXML private ImageView login_exit;
    @FXML private ImageView login_button;

    // Login 하는 부분
    public void Login(MouseEvent event) throws Exception{
    	int i=0;
    	
    	for(i=0; i<DataManager.memCount; i++) {
    		if(txtUserName.getText().equals(DataManager.member[i].Identification) && txtPassword.getText().equals(temp_password)) {
    			prevStage.close();
    			
    			Main_page main = new Main_page();
    		}
    	}
    	if(i>=DataManager.memCount)
    		lblStatus.setText("Login Failed");
    	
    }

    public void UserName_Clicked(MouseEvent event) throws Exception{
    	Main.keyboard_flag=0;
    	Main.keyboard_temp="";
    	
    	try {			
			keyboardStage = new Stage();
			
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/virtual_keyboard.fxml"));
			Parent root = (Parent)myLoader.load();
			
			KeyboardController k_controller = (KeyboardController) myLoader.getController();
            k_controller.setPrevStage(keyboardStage);
			
			Scene scene = new Scene(root);

			keyboardStage.setScene(scene);
			keyboardStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public void Password_Clicked(MouseEvent event) throws Exception{
    	Main.keyboard_flag=1;
    	try {			
			keyboardStage = new Stage();
			
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/virtual_keyboard.fxml"));
			Parent root = (Parent)myLoader.load();
			
			KeyboardController k_controller = (KeyboardController) myLoader.getController();
            k_controller.setPrevStage(keyboardStage);
			
			Scene scene = new Scene(root);

			keyboardStage.setScene(scene);
			keyboardStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    public void setBackgroundImage() {
    	
    	login_exit.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                System.out.println("exit this App at Loginpage");
                System.exit(1);
            }

        });
    }
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
   }
    public void setUserName(String temp) {
    	txtUserName.setText(temp);
    }
    public void setPassword(String temp) {
    	txtPassword.setText(temp);
    }
}