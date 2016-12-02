package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Member_ui {
	
	private Stage primaryStage;
	private BorderPane member_main_border = new BorderPane();
	private BorderPane member_left_border, choice_border;
	private GridPane member_right_grid;
	private Button add_button, detail_button;
	private ImageView detail_image, add_image, member_image;
	private Label member_id_label, member_info_label, calorie_label, carbohydrate_label, protein_label, fat_label;
	private ComboBox<String> choice_period = new ComboBox<String>(FXCollections.observableArrayList("1일", "1주", "4주"));
	private int choice_arr[]={1, 7, 28};	
	
	public Member_ui() {

		Image add_img = new Image("/image/memberadd_button.png");
		add_image = new ImageView(add_img);	add_image.setFitHeight(65);	add_image.setFitWidth(65);
		BorderPane sub_border = new BorderPane();
		member_main_border.setPrefSize(450, 200);
		sub_border.setCenter(add_image);
		member_main_border.setCenter(sub_border);
		member_main_border.getStyleClass().add("borderpane");
		
		this.add_image.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                Add_page sub = new Add_page();
            }
        });
	}
	public Member_ui(String id, String name, String sex, int age, double calorie, double carbohydrate, double protein, double fat, int this_index) {
		member_left_border = new BorderPane();	choice_border = new BorderPane();
		member_id_label = new Label();	member_id_label.setPrefSize(120, 10); 
		member_info_label = new Label(); calorie_label = new Label(); carbohydrate_label = new Label(); 
		protein_label = new Label(); fat_label = new Label();
		detail_button = new Button("상세보기");
		
		choice_period.setPromptText("기준");
		choice_period.setPrefSize(90,10);
		
		set_member_image(id);
		
		member_image.setFitHeight(150);	member_image.setFitWidth(110);
		member_main_border.setPrefSize(450, 200);
		member_left_border.setCenter(member_image);
		member_left_border.setBottom(member_id_label);
		member_right_grid = new GridPane();	member_right_grid.setHgap(10);	member_right_grid.setVgap(10);
		
		member_main_border.getStyleClass().add("borderpane");
		
		
		choice_period.getSelectionModel().selectedIndexProperty()
        .addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
              set_choice(choice_arr[new_value.intValue()]);
              System.out.printf("ID = %s\tchoice = %d\n", id, get_choice());
            }
          });
		
		Image detail_img = new Image("/image/detail.png");
		detail_image = new ImageView(detail_img);	detail_image.setFitHeight(30);	detail_image.setFitWidth(75);
		
		
		choice_border.setCenter(choice_period);
		choice_border.setRight(detail_image);
		choice_border.setMargin(choice_period, new Insets(0,20,0,50));

		Main.id_index=this_index;
		
		set_id_label(id);
		set_member_info_label(name, sex, age);
		set_calorie_label(calorie);
		set_carbohydrate_label(carbohydrate);
		set_protein_label(protein);
		set_fat_label(fat);
		
		member_right_grid.add(get_member_info_label(), 0, 0);	
		member_right_grid.add(get_calorie_label(), 0, 2);
		member_right_grid.add(get_carbohydrate_label(), 0, 3);
		member_right_grid.add(get_protein_label(), 0, 4);
		member_right_grid.add(get_fat_label(), 0, 5);
		member_right_grid.add(choice_border, 0, 7);

		member_main_border.setLeft(member_left_border);	member_main_border.setRight(member_right_grid);
		member_main_border.setMargin(member_right_grid, new Insets(0,30,0,20));
		member_main_border.setPadding(new Insets(5,5,10,10));	member_right_grid.setPadding(new Insets(10,0,0,0));
		member_main_border.getStyleClass().add("borderpane");

		
		this.detail_image.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
            public void handle(MouseEvent event) {
				
				Main.id_index = this_index;
				
            	if(get_choice()==1) {
            		try {
            			primaryStage = new Stage();
                       	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/select_date.fxml"));
                        Parent root = (Parent)myLoader.load();
                        
                        SelectDateController controller = (SelectDateController) myLoader.getController();
                        controller.setPrevStage(primaryStage);
                        
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("style/popup.css").toExternalForm());
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
            	}
            	else {
            		Sub_page sub = new Sub_page();
            	}
            }
        });
	}
	
	BorderPane get_main_border() {
		return member_main_border;
	}
	Label get_id_label() {
		member_id_label.getStyleClass().add("font");
		return member_id_label;
	}
	Label get_member_info_label() {
		member_info_label.getStyleClass().add("font");
		return member_info_label;
	}
	Label get_calorie_label() {
		calorie_label.getStyleClass().add("font");
		return calorie_label;
	}
	Label get_carbohydrate_label() {
		carbohydrate_label.getStyleClass().add("font");
		return carbohydrate_label;
	}
	Label get_protein_label() {
		protein_label.getStyleClass().add("font");
		return protein_label;
	}
	Label get_fat_label() {
		fat_label.getStyleClass().add("font");
		return fat_label;
	}
 
	ComboBox<String> get_combo_box() {
		return choice_period;
	}
	
	int get_choice() {
		return Main.choice;
	}
	void set_member_image(String id) {
		String i_name = "/image/";
		i_name += id;
		i_name += ".jpg";
		
		member_image = new ImageView(new Image(i_name));

	}
	void set_id_label(String id) {
		member_id_label.getStyleClass().add("font");
		member_id_label.setText("ID : "+id);
	}
	void set_member_info_label(String name, String sex, int age) {
		member_info_label.getStyleClass().add("font");
		member_info_label.setText("이름 : "+name+"\t성별 : "+sex+"\t나이 : "+age);
	}
	void set_calorie_label(double calorie) {
		calorie_label.getStyleClass().add("font");
		calorie_label.setText("열량        : "+Double.toString(calorie)+" Kcal" + "\t("+DataManager.member[Main.id_index].get_my_collection()[0]+" Kcal)");
	}
	void set_carbohydrate_label(double carbohydrate) {
		carbohydrate_label.getStyleClass().add("font");
		String temp = Double.toString(carbohydrate);
		if(temp.length()>6)
			carbohydrate_label.setText("탄수화물  : "+temp.substring(0, temp.length()-12)+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[1]+" g)");
		else
			carbohydrate_label.setText("탄수화물  : "+temp+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[1]+" g)");
	}
	void set_protein_label(double protein) {
		protein_label.getStyleClass().add("font");
		String temp = Double.toString(protein);
		if(temp.length()>6)
			protein_label.setText("단백질     : "+temp.substring(0, temp.length()-12)+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[2]+" g)");
		else
			protein_label.setText("단백질     : "+temp+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[2]+" g)");
	}
	void set_fat_label(double fat) {
		String temp = Double.toString(fat);
		if(temp.length()>6)
			fat_label.setText("지방        : "+temp.substring(0, temp.length()-12)+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[3]+" g)");
		else
			fat_label.setText("지방        : "+temp+" g" + "\t\t("+DataManager.member[Main.id_index].get_my_collection()[3]+" g)");
		fat_label.getStyleClass().add("font");
	}
	void set_choice(int choice) {
		Main.choice=choice;
	}
}