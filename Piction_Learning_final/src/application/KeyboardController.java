package application;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class KeyboardController {
	Stage prevStage;
	
	 public void setPrevStage(Stage stage){
	        this.prevStage = stage;
	   }
	   public void Button_1_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="1";
	   }
	   public void Button_2_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="2";
	   }
	   public void Button_3_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="3";
	   }
	   public void Button_4_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="4";
	   }
	   public void Button_5_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="5";
	   }
	   public void Button_6_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="6";
	   }
	   public void Button_7_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="7";
	   }
	   public void Button_8_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="8";
	   }
	   public void Button_9_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="9";
	   }
	   public void Button_0_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="0";
	   }
	   public void Button_q_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="q";
	   }
	   public void Button_w_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="w";
	   }
	   public void Button_e_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="e";
	   }
	   public void Button_r_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="r";
	   }
	   public void Button_t_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="t";
	   }
	   public void Button_y_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="y";
	   }
	   public void Button_u_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="u";
	   }
	   public void Button_i_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="i";
	   }
	   public void Button_o_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="o";
	   }
	   public void Button_p_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="p";
	   }
	   public void Button_a_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="a";
	   }
	   public void Button_s_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="s";
	   }
	   public void Button_d_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="d";
	   }
	   public void Button_f_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="f";
	   }
	   public void Button_g_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="g";
	   }
	   public void Button_h_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="h";
	   }
	   public void Button_j_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="j";
	   }
	   public void Button_k_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="k";
	   }
	   public void Button_l_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="l";
	   }
	   public void Button_z_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="z";
	   }
	   public void Button_x_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="x";
	   }
	   public void Button_c_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="c";
	   }
	   public void Button_v_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="v";
	   }
	   public void Button_b_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="b";
	   }
	   public void Button_n_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="n";
	   }
	   public void Button_m_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp+="m";
	   }
	   public void Button_back_Clicked(MouseEvent event) throws Exception {
		   String newstr="";
		   newstr=Main.keyboard_temp.substring(0, Main.keyboard_temp.length()-1);
		   Main.keyboard_temp=newstr;
	   }
	   public void Button_cancel_Clicked(MouseEvent event) throws Exception {
		   Main.keyboard_temp="";
		   prevStage.close();
		   if(Main.keyboard_flag==0)
			   Main.controller.setUserName("");
		   else
			   Main.controller.setPassword("");
	   }
	   public void Button_confirm_Clicked(MouseEvent event) throws Exception {
		   prevStage.close();
		   if(Main.keyboard_flag==0)
			   Main.controller.setUserName(Main.keyboard_temp);
		   else
			   Main.controller.setPassword(Main.keyboard_temp);
		   Main.keyboard_temp="";
	   }
}
