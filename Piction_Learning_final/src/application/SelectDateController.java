package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SelectDateController implements Initializable{
	Stage prevStage;
	@FXML ComboBox<String> choice_date;

	String date_arr[] = DataManager.member[0].eachMonthdate("2016-11-20");
	ObservableList<String> date_list = FXCollections.observableArrayList(date_arr);
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		choice_date.setItems(date_list);    
		choice_date.setPromptText("날짜선택");
		choice_date.getSelectionModel().selectedIndexProperty()
        .addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
              Main.selected_date=date_arr[new_value.intValue()];
              System.out.printf("selected date = %s\n", Main.selected_date);
            }
          });
    }
	public void Selected_date(MouseEvent event) throws Exception {	
		System.out.printf("selected_date = %s", Main.selected_date);
    	System.out.println("success select");
    	Sub_page sub = new Sub_page();
    	prevStage.close();
    }
	
	public void Cancel_select(MouseEvent event) throws Exception {
    	System.out.println("exit select_page");
    	prevStage.close();
    }
	
	public void setPrevStage(Stage stage){
        this.prevStage = stage;
   }
}
