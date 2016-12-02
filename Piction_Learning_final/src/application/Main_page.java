package application;
	
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class Main_page {
	
	private AnchorPane background_top_right;	// background_top_sub_right 들어갈 공간
	private BorderPane background_top_sub_border, background_top_sub_right;	// title버튼 들어갈 공간, 업데이트 버튼, exit버튼 들어갈 공간 
	private GridPane background_grid;	// 회원 정보들 들어갈 공간
	private Label title_label = new Label("My Group");
	private ScrollPane main_scrollpane;
	private ImageView exit_image, update_image;
	private Member_ui member[] = new Member_ui[6];
	
	public Main_page() {
		try {
			Stage primaryStage = new Stage();
			
			BorderPane root = new BorderPane();		main_scrollpane = new ScrollPane();
			background_top_sub_border = new BorderPane();	background_top_right = new AnchorPane();
			background_top_sub_right = new BorderPane();	background_grid = new GridPane();
			main_scrollpane.setPrefSize(800, 450);
			main_scrollpane.getStyleClass().add("scrollpane");
			background_top_sub_border.getStyleClass().add("mygroupborderpane");
			
			background_grid.getStyleClass().add("borderpane");
			
			Image exit_img = new Image("/image/exit_button.png");
			exit_image = new ImageView(exit_img);	exit_image.setFitHeight(30);	exit_image.setFitWidth(30);
			Image update_img = new Image("/image/refresh.png");
			update_image = new ImageView(update_img);	update_image.setFitHeight(30);

			load_main_page();
			
			background_grid.getStyleClass().add("root");
			
			main_scrollpane.setContent(background_grid);
			main_scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);	main_scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
			
			this.exit_image.setOnMousePressed(new EventHandler<MouseEvent>() {

	            public void handle(MouseEvent evt) {
	                System.out.println("exit this App at Mainpage");
	                System.exit(1);
	            }

	        });
			
			this.update_image.setOnMousePressed(new EventHandler<MouseEvent>() {

	            public void handle(MouseEvent evt) {
	                System.out.println("clicked update image at Mainpage");
	                DataManager.groupinfo = new String[6][4];
	                DataManager.memNutritionInfo = new String[6][180][3];
	                DataManager.NutritionInfo = new double[6][180][10];
	                
	                ClientManager connection = new ClientManager();
	            	connection.connect();
	                
	                for(int i=0; i<DataManager.memCount; i++) {
	                	Main.id_index = i;
	                	member[i].set_member_image(DataManager.member[i].Identification);
	                	member[i].set_id_label(DataManager.member[i].Identification);
	                	member[i].set_member_info_label(DataManager.member[i].name, (DataManager.member[i].sex.equals("M") ? "남" : "여"), DataManager.member[i].age);
	                	member[i].set_calorie_label(get_today_calorie(i));
	                	member[i].set_carbohydrate_label(get_today_carbohydrate(i));
	                	member[i].set_protein_label(get_today_protein(i));
	                	member[i].set_fat_label(get_today_fat(i));
	                }
	            }
	        });
			
			title_label.setFont(new Font("Bauhaus 93", 20));
			
			background_top_sub_right.setCenter(update_image);
			background_top_sub_right.setRight(exit_image);
			background_top_sub_right.setMargin(update_image, new Insets(5,10,5,0));
			background_top_sub_right.setMargin(exit_image, new Insets(5,10,5,0));
			
			background_top_right.getChildren().add(background_top_sub_right);
			
			background_top_sub_border.setMargin(title_label, new Insets(0, 0, 0, 120));
			background_top_sub_border.setPadding(new Insets(5,0,0,0));
			background_top_sub_border.setCenter(title_label);
			background_top_sub_border.setRight(background_top_right);
			
			root.setTop(background_top_sub_border);
			root.setCenter(main_scrollpane);
			
			Scene scene = new Scene(root,800,480);
			scene.getStylesheets().add(getClass().getResource("style/main.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void load_main_page() {
		for(int i=0; i<6; i++) {
			
			if(i<DataManager.memCount) {
				String id, name, sex;
				int age;
				double calorie, carbohydrate, protein, fat;
			
				id = DataManager.member[i].Identification;
				name = DataManager.member[i].name;
				age = DataManager.member[i].age;
				if(DataManager.member[i].sex.equals("M"))
					sex = "남";
				else
					sex = "여";
				calorie = get_today_calorie(i);
				carbohydrate = get_today_carbohydrate(i);
				protein = get_today_protein(i);
				fat = get_today_fat(i);
				
				member[i] = new Member_ui(id, name, sex, age, calorie, carbohydrate, protein, fat, i);
			}
			else
				member[i] = new Member_ui();
		}
		background_grid.add(member[0].get_main_border(), 0, 0);
		background_grid.add(member[1].get_main_border(), 0, 1);
		background_grid.add(member[2].get_main_border(), 1, 0);
		background_grid.add(member[3].get_main_border(), 1, 1);
		background_grid.add(member[4].get_main_border(), 2, 0);
		background_grid.add(member[5].get_main_border(), 2, 1);
	}
	public double get_today_calorie(int i) {
		return DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[0][1]+DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[1][1]+
				DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[2][1];
	}
	public double get_today_carbohydrate(int i) {
		return DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[0][2]+DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[1][2]+
				DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[2][2];
	}
	public double get_today_protein(int i) {
		return DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[0][3]+DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[1][3]+
				DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[2][3];
	}
	public double get_today_fat(int i) {
		return DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[0][4]+DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[1][4]+
				DataManager.member[i].eachDayFoodNutrition(Main.today_Date)[2][4];
	}
}
