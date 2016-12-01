package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Sub_page {
	
	private Stage primaryStage;
	GraphController controller;
	private double calorie_values[]={1000, 1500, 1300};
	
	public Sub_page() {

			open_graph(Main.choice);
			
			if(Main.choice==1)
			{
				controller.set_select_date(Main.selected_date);
			}
	}
	
	public void open_graph(int choice) {
		try {			
			primaryStage = new Stage();
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/detail_graph.fxml"));
			Parent root = (Parent)myLoader.load();
			
			controller = (GraphController) myLoader.getController();
            controller.setPrevStage(primaryStage);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("style/subpage.css").toExternalForm());
			
			primaryStage.setTitle("ChartControl");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
