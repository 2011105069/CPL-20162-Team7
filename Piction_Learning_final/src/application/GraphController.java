package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GraphController implements Initializable {
	Stage prevStage;
	
	private XYChart.Series<String, Number> calorie_series, carbohydrate_series, protein_series, fat_series;
	private XYChart.Series<String, Number> fiber_series, vita_c_series, ca_series, na_series, fe_series;
	
	private String chart_label_01[]=new String[3];
	private String chart_label_07[]=new String[7];
	private String chart_label_28[]={"지난 1주", "지난 2주", "지난 3주", "지난 4주"};
	private double calorie_values_01[]=new double[3];
	private double calorie_values_07[]=new double[7];
	private double calorie_values_28[]=new double[4];
	private double carbohydrate_values_01[]=new double[3];
	private double carbohydrate_values_07[]=new double[7];
	private double carbohydrate_values_28[]=new double[4];
	private double protein_values_01[]=new double[3];
	private double protein_values_07[]=new double[7];
	private double protein_values_28[]=new double[4];
	private double fat_values_01[]=new double[3];
	private double fat_values_07[]=new double[7];
	private double fat_values_28[]=new double[4];
	private double fiber_values_01[]=new double[3];
	private double fiber_values_07[]=new double[7];
	private double fiber_values_28[]=new double[4];
	private double vita_c_values_01[]=new double[3];
	private double vita_c_values_07[]=new double[7];
	private double vita_c_values_28[]=new double[4];
	private double ca_values_01[]=new double[3];
	private double ca_values_07[]=new double[7];
	private double ca_values_28[]=new double[4];
	private double na_values_01[]=new double[3];
	private double na_values_07[]=new double[7];
	private double na_values_28[]=new double[4];
	private double fe_values_01[]=new double[3];
	private double fe_values_07[]=new double[7];
	private double fe_values_28[]=new double[4];
	
	@FXML private LineChart<String, Number> CalroieChart;
	@FXML private LineChart<String, Number> MainNutriChart;
	@FXML private LineChart<String, Number> SubNutriChart;
	@FXML private ScrollPane main_scroll;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		calorie_series = new XYChart.Series<String, Number>();
		carbohydrate_series = new XYChart.Series<String, Number>();
		protein_series = new XYChart.Series<String, Number>();
		fat_series = new XYChart.Series<String, Number>();
		fiber_series = new XYChart.Series<String, Number>();
		vita_c_series = new XYChart.Series<String, Number>();
		ca_series = new XYChart.Series<String, Number>();
		na_series = new XYChart.Series<String, Number>();
		fe_series = new XYChart.Series<String, Number>();
		
		System.out.printf("id_index = %d", Main.id_index);
		if(Main.choice==1) {
			chart_label_01=DataManager.member[Main.id_index].eachDayFoodName(Main.selected_date);
			for(int i=0; i<3; i++) {
				calorie_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][1];
				carbohydrate_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][2];
				protein_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][3];
				fat_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][4];
				fiber_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][5];
				vita_c_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][6];
				ca_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][7];
				na_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][8];
				fe_values_01[i]=DataManager.member[Main.id_index].eachDayFoodNutrition(Main.selected_date)[i][9];
			}
			set_CalorieChart(chart_label_01, calorie_values_01);
			set_MainNutriChart(chart_label_01, carbohydrate_values_01, protein_values_01, fat_values_01);
			set_SubNutriChart(chart_label_01, fiber_values_01, vita_c_values_01, ca_values_01, na_values_01, fe_values_01);
		}
		else if(Main.choice==7) {
			String[] new_label = new String [7];
			
			for(int i=0; i<7; i++)
				new_label[i]="";
			chart_label_07=DataManager.member[Main.id_index].eachWeekdate(Main.selected_date);
			
			for(int i=0; i<7; i++) {
				new_label[i] += chart_label_07[i].substring(0, 4);
				new_label[i] += "\n";
				new_label[i] += chart_label_07[i].substring(5);
			}
			for(int i=0; i<7; i++) {
				calorie_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][1];
				carbohydrate_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][2];
				protein_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][3];
				fat_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][4];
				fiber_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][5];
				vita_c_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][6];
				ca_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][7];
				na_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][8];
				fe_values_07[i]=DataManager.member[Main.id_index].eachWeekNutrition(Main.selected_date)[i][9];
			}
			set_CalorieChart(new_label, calorie_values_07);
			set_MainNutriChart(new_label, carbohydrate_values_07, protein_values_07, fat_values_07);
			set_SubNutriChart(new_label, fiber_values_07, vita_c_values_07, ca_values_07, na_values_07, fe_values_07);
		}
		else if(Main.choice==28) {
			for(int i=0; i<4; i++) {
				calorie_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][1];
				carbohydrate_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][2];
				protein_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][3];
				fat_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][4];
				fiber_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][5];
				vita_c_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][6];
				ca_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][7];
				na_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][8];
				fe_values_28[i]=DataManager.member[Main.id_index].eachMonthNutrition(Main.selected_date)[i][9];
			}
			set_CalorieChart(chart_label_28, calorie_values_28);
			set_MainNutriChart(chart_label_28, carbohydrate_values_28, protein_values_28, fat_values_28);
			set_SubNutriChart(chart_label_28, fiber_values_28, vita_c_values_28, ca_values_28, na_values_28, fe_values_28);
		}
 	}
	
	public void Detail_exit(MouseEvent event) throws Exception {
		Main.selected_date="2016-11-20";
    	System.out.println("exit detail_page");
    	prevStage.close();
    }
	
	public void set_CalorieChart(String chart_label[], double calorie_values[]) {
		int i;

		calorie_series.setName("열량");
		String new_label[] = {"아침_", "점심_", "저녁_"};
		if(Main.choice==1)
			for(i=0; i<chart_label.length; i++)
				new_label[i]+=chart_label[i];
		for(i=0; i<chart_label.length; i++) {
			if(Main.choice==1)
				calorie_series.getData().add(new XYChart.Data<String, Number>(new_label[i], calorie_values[i]));
			else
				calorie_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], calorie_values[i]));
		}

		CalroieChart.getData().add(calorie_series);
	}
	public void set_MainNutriChart(String chart_label[], double carbohydrate_values[], double protein_values[], double fat_values[]) {
		int i;
		
		MainNutriChart.getData().clear();
		
		carbohydrate_series.setName("탄수화물");
		protein_series.setName("단백질");
		fat_series.setName("지방");
		
		String new_label[] = {"아침_", "점심_", "저녁_"};
		
		if(Main.choice==1)
			for(i=0; i<chart_label.length; i++)
				new_label[i]+=chart_label[i];
		
		for(i=0; i<chart_label.length; i++) {
			if(Main.choice==1)
			{
				carbohydrate_series.getData().add(new XYChart.Data<String, Number>(new_label[i], carbohydrate_values[i]));
				protein_series.getData().add(new XYChart.Data<String, Number>(new_label[i], protein_values[i]));
				fat_series.getData().add(new XYChart.Data<String, Number>(new_label[i], fat_values[i]));
			}
			else
			{
				carbohydrate_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], carbohydrate_values[i]));
				protein_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], protein_values[i]));
				fat_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], fat_values[i]));
			}
		}
		
		MainNutriChart.getData().addAll(carbohydrate_series, protein_series, fat_series);
	}
	
	public void set_SubNutriChart(String chart_label[], double fiber_values[], double vita_c_values[], double ca_values[], double na_values[], double fe_values[]) {
		int i;
		
		fiber_series.setName("식이섬유");
		vita_c_series.setName("비타민C");
		ca_series.setName("칼슘");
		na_series.setName("나트륨");
		fe_series.setName("철");
		
		String new_label[] = {"아침_", "점심_", "저녁_"};
		if(Main.choice==1)
			for(i=0; i<chart_label.length; i++)
				new_label[i]+=chart_label[i];
		
		for(i=0; i<chart_label.length; i++) {
			if(Main.choice==1)
			{
				fiber_series.getData().add(new XYChart.Data<String, Number>(new_label[i], fiber_values[i]));
				vita_c_series.getData().add(new XYChart.Data<String, Number>(new_label[i], vita_c_values[i]));
				ca_series.getData().add(new XYChart.Data<String, Number>(new_label[i], ca_values[i]));
				na_series.getData().add(new XYChart.Data<String, Number>(new_label[i], na_values[i]));
				fe_series.getData().add(new XYChart.Data<String, Number>(new_label[i], fe_values[i]));
			}
			else {
				fiber_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], fiber_values[i]));
				vita_c_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], vita_c_values[i]));
				ca_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], ca_values[i]));
				na_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], na_values[i]));
				fe_series.getData().add(new XYChart.Data<String, Number>(chart_label[i], fe_values[i]));
			}
		}
		
		SubNutriChart.getData().addAll(fiber_series, vita_c_series, ca_series, na_series, fe_series);
	}
	public void setPrevStage(Stage stage){
        this.prevStage = stage;
   }
	public void set_select_date(String select_date) {
		Main.selected_date=select_date;
	}
}
