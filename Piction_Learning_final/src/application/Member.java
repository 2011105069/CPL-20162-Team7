package application;

import java.util.*;
import java.text.*; 


public class Member {

	//ID
	//이름, 성별, 나이
	//열량, 탄수화물, 지방, 단백질
	String Identification = new String();
	String name = new String();
	String sex = new String();
	int age = 0;
	
	String eachNutritionInfo[][] = new String[180][2];	//날짜, 음식이름 입력됨.
	double NutritionInfo[][] = new double[180][10];		//인분, 열량, 탄, 단, 지, 식, 비C, 칼슘, 나트륨, 철 입력됨.
	
	double eachFoodSum[] = new double[3];
	double dailySum[] = new double[7];
	double weeklySum[] = new double[4];
	
	public Member(int i, int dataCount)
	{
		Identification = DataManager.groupinfo[i][0];
		name = DataManager.groupinfo[i][1];
		sex = DataManager.groupinfo[i][2];
		age = Integer.parseInt(DataManager.groupinfo[i][3]);
		
		for(int count1 = 0 ; count1 < dataCount ; count1++)
			for(int count2 = 0; count2 < 2; count2 ++)
				eachNutritionInfo[count1][count2] = DataManager.memNutritionInfo[i][count1][(count2)+1];
		// 이거는 날짜, 음식이름이 들어가 있음.
		
		for(int count1 = 0 ; count1 < dataCount ; count1++)
			for(int count2 = 0; count2 < 10; count2 ++)
					NutritionInfo[count1][count2] = DataManager.NutritionInfo[i][count1][count2];
		// 이거는 인분, 열량, 탄수화물, 단백질, 지방, 식이섬유, 비타민C, 칼슘, 나트륨, 철 들어가 있음.
		
		System.out.println(Identification+ "입력 완료 ");
		
	}
	
	
	
	public String[] eachDayFoodName(String date)
	{
		String[] foodNameString = new String[3];
		int count2 = 0;
		//만약 같은 날짜에 있다면 음식별로 영양정보랑 음식 이름을 줘야함. 
		for(int count1 = 0; count1<180; count1++)
		{	
			if(date.equals(eachNutritionInfo[count1][0]))
			{
				foodNameString[count2] = eachNutritionInfo[count1][1];	
				count2++;
			}
		}
		
		return foodNameString;
	}

	public double[][] eachDayFoodNutrition(String date)
	{
		///////////////////////////////////////////////////////////
		double[][] foodNutritionInfo = new double[3][10];
		int i = 0;
		
		for(int count1 = 0; count1<180; count1++)
		{
			if(date.equals(eachNutritionInfo[count1][0]))
			{
				for(int count2 = 0; count2 <10; count2++)
				{
					foodNutritionInfo[i][count2] += NutritionInfo[count1][count2];
				}
				i++;
			}
		}
		
		return foodNutritionInfo;
	}
	
	public double[] eachDayCalculate(String date)
	{
		double eachFoodSum[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		
		//만약 같은 날짜에 있다면 음식별로 영양정보랑 음식 이름을 줘야함. 
		for(int count1 = 0; count1<180; count1++)
		{
			if(date.equals(eachNutritionInfo[count1][0]))
			{
				for(int count2 = 0; count2 <10; count2++)
				{
					eachFoodSum[count2] += NutritionInfo[count1][count2];
				}
			}
		}
		
		return eachFoodSum;
	}
	
	public String[] eachWeekdate(String date)
	{
		String[] weekDate = new String[7];
		String temp = null;
		
		for(int i= 0; i<7; i++)
		{
			temp = dateMinusOne(date);
			weekDate[i] = temp;
			date = temp;
		}
		
		return weekDate;
	}
	
	// 7일간 같은 날짜 끼리 더해서 보여줘야댐.
	// 여기서 필요한 건 오늘날짜 받아와서  혹은 특정한 날짜 받아와서 그 날 전부터 하루간 총 배열 double[7][9]에 저장해서 이걸 return 해주면 되겠네.
	///////////////////////////////////////////////////////////////////////////////////////
	public double[][] eachWeekNutrition(String date)
	{
		double[][] weekNutritionSum = new double[7][10];
		String tempDate = null;
		for(int i=0 ; i< 7; i++)
		{
			tempDate = dateMinusOne(date);
			for(int j=0 ; j<10; j++)
			{
				weekNutritionSum[i][j] = eachDayCalculate(tempDate)[j]; 
			}
			date = tempDate;
		}
		
		return weekNutritionSum;
	}
	
	// 7일간씩 평균값 낸 것을 4개 보여줘야댐.
	// 여기서 필요한 건 첫째주 둘째주 셋째주 넷째주니까. double[4][9]에 저장해야 되는데, 1개에 7일간꺼를 다 더하면 되겠네.
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public double[][] eachMonthNutrition(String date)
	{
		double[][] monthNutritionSum = new double[4][10];
		String tempDate = null;
		//eachWeekNutrition에서 date, date -7 , date -14, date -21를 넣어서 더하면 되겠네. 
		for(int i=0 ; i< 4; i++)
		{
			for(int j=0 ; j<7; j++)
			{
				for(int k=0; k< 10; k++)
					monthNutritionSum[i][k] += eachWeekNutrition(date)[j][k]; 
			}
			
			tempDate = dateMinusSeven(date);
			date = tempDate;
			
			for(int l = 0 ; l<10; l++)
				monthNutritionSum[i][l] /= 7;
		}
		return monthNutritionSum;
	}
	
	
	//오늘 날짜 가져와서 날짜 계산하는 함수 짜야될듯.
	
	public static String dateToString(Date specificDate)
	{
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		String dTime = formatter.format ( specificDate );
		
		return dTime;
	}
	
	public static String dateMinusOne(String date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd" ); 
		
		ParsePosition pos = new ParsePosition ( 0 );
		Date frmTime = formatter.parse ( date, pos );
		
		Date yesterday = addDate(frmTime, -1);
		String result = dateToString(yesterday);
		
		return result; 
	}
	
	public static String dateMinusSeven(String date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd" ); 
		
		ParsePosition pos = new ParsePosition ( 0 );
		Date frmTime = formatter.parse ( date, pos );
		
		Date before7day = addDate(frmTime, -7);
		String result = dateToString(before7day);
		
		return result; 
	}
	public static String[] eachMonthdate(String date)
	   {
	      String[] monthDate = new String[29];
	      String temp = null;
	      
	      for(int i=0 ; i< 29; i++)
	      {
	         temp=date;
	         monthDate[i] = temp;

	         date = dateMinusOne(date);
	      }
	      
	      return monthDate;
	   }
	
	public static Date addDate(Date date, int dates)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dates);
		
		return cal.getTime();
	}
	public double[] get_my_collection() {
		if(age<10 && sex.equals("M"))
			return DataCollection.man;
		else if(age>=10 && age<20 && sex.equals("M"))
			return DataCollection.man10;
		else if(age>=20 && age<30 && sex.equals("M"))
			return DataCollection.man20;
		else if(age>=30 && age<40 && sex.equals("M"))
			return DataCollection.man30;
		else if(age>=40 && age<50 && sex.equals("M"))
			return DataCollection.man40;
		else if(age>=50 && age<60 && sex.equals("M"))
			return DataCollection.man50;
		else if(age>=60 && age<70 && sex.equals("M"))
			return DataCollection.man60;
		else if(age>=70 && sex.equals("M"))
			return DataCollection.man70;
		else if(age<10 && sex.equals("F"))
			return DataCollection.woman;
		else if(age<20 && age>=10 && sex.equals("F"))
			return DataCollection.woman10;
		else if(age<30 && age>=20 && sex.equals("F"))
			return DataCollection.woman20;
		else if(age<40 && age>=30 && sex.equals("F"))
			return DataCollection.woman30;
		else if(age<50 && age>=40 && sex.equals("F"))
			return DataCollection.woman40;
		else if(age<60 && age>=50 && sex.equals("F"))
			return DataCollection.woman50;
		else 
			return DataCollection.woman60;
	}
}
