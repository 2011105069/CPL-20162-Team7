package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddMemberController {
	Stage prevStage;

	@FXML private TextField txtUserName;
	
	public void Add_group_member(MouseEvent event) throws Exception {
		System.out.println("Add User ID : "+txtUserName.getText());		
		
    	System.out.println("success add");
    	prevStage.close();
    }
	
	public void Cancel_add(MouseEvent event) throws Exception {
    	System.out.println("exit add_page");
    	prevStage.close();
    }
	
	public void setPrevStage(Stage stage){
	    this.prevStage = stage;
	}
}