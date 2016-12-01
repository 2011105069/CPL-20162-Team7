package application;

import java.util.*;
import java.text.*; 


public class Member {

	//ID
	//�̸�, ����, ����
	//����, ź��ȭ��, ����, �ܹ���
	String Identification = new String();
	String name = new String();
	String sex = new String();
	int age = 0;
	
	String eachNutritionInfo[][] = new String[180][2];	//��¥, �����̸� �Էµ�.
	double NutritionInfo[][] = new double[180][10];		//�κ�, ����, ź, ��, ��, ��, ��C, Į��, ��Ʈ��, ö �Էµ�.
	
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
		// �̰Ŵ� ��¥, �����̸��� �� ����.
		
		for(int count1 = 0 ; count1 < dataCount ; count1++)
			for(int count2 = 0; count2 < 10; count2 ++)
					NutritionInfo[count1][count2] = DataManager.NutritionInfo[i][count1][count2];
		// �̰Ŵ� �κ�, ����, ź��ȭ��, �ܹ���, ����, ���̼���, ��Ÿ��C, Į��, ��Ʈ��, ö �� ����.
		
		System.out.println(Identification+ "�Է� �Ϸ� ");
		
	}
	
	
	
	public String[] eachDayFoodName(String date)
	{
		String[] foodNameString = new String[3];
		int count2 = 0;
		//���� ���� ��¥�� �ִٸ� ���ĺ��� ���������� ���� �̸��� �����. 
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
		
		//���� ���� ��¥�� �ִٸ� ���ĺ��� ���������� ���� �̸��� �����. 
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
	
	// 7�ϰ� ���� ��¥ ���� ���ؼ� ������ߴ�.
	// ���⼭ �ʿ��� �� ���ó�¥ �޾ƿͼ�  Ȥ�� Ư���� ��¥ �޾ƿͼ� �� �� ������ �Ϸ簣 �� �迭 double[7][9]�� �����ؼ� �̰� return ���ָ� �ǰڳ�.
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
	
	// 7�ϰ��� ��հ� �� ���� 4�� ������ߴ�.
	// ���⼭ �ʿ��� �� ù°�� ��°�� ��°�� ��°�ִϱ�. double[4][9]�� �����ؾ� �Ǵµ�, 1���� 7�ϰ����� �� ���ϸ� �ǰڳ�.
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public double[][] eachMonthNutrition(String date)
	{
		double[][] monthNutritionSum = new double[4][10];
		String tempDate = null;
		//eachWeekNutrition���� date, date -7 , date -14, date -21�� �־ ���ϸ� �ǰڳ�. 
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
	
	
	//���� ��¥ �����ͼ� ��¥ ����ϴ� �Լ� ¥�ߵɵ�.
	
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
